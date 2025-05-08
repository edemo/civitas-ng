/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import civitas.crypto.CryptoException;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.ProofVote;
import civitas.crypto.VoteCapability;

/**
 * This class is the ballot design of an approval race. In an approval race,
 * there is a slate of candidates, and the voter indicates approval or
 * disapproval of each candidate. An ApprovalBallotDesign describes the
 * candidates in the race.
 */
public class ApprovalBallotDesign extends BallotDesign {
	public static final String KIND = "approval";
	private static final String CONTEXT_SUFFIX = "approval:";

	public final String[] candidates;

	public ApprovalBallotDesign(String[] candidates) {
		super();
		String[] cs;
		if (candidates != null) {
			cs = candidates.clone();
		} else {
			cs = new String[0];
		}
		this.candidates = cs;
	}

	public int numberOfCandidates() {
		if (candidates == null)
			return 0;
		return candidates.length;
	}

	/**
	 * Return the index of the candidate cand, -1 if cand is not a candidate.
	 */
	public int indexOfCandidate(String cand) {
		if (candidates == null)
			return -1;
		for (int i = 0; i < candidates.length; i++) {
			try {
				String cD = candidates[i];
				if (cD == cand || (cD != null && cD.equalsIgnoreCase(cand))) {
					return i;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return -1;
	}

	@Override
	public void checkBallot(Ballot b) throws IllegalArgumentException {
		if (!(b instanceof ApprovalBallot)) {
			throw new IllegalArgumentException("Ballot is not an approval ballot.");
		}

		ApprovalBallot ab = (ApprovalBallot) b;
		String[] abCandidates = ab.candidates;
		boolean[] abApproved = ab.approved;

		if (abCandidates == null || abApproved == null || candidates == null) {
			throw new IllegalArgumentException("Missing slate.");
		}

		if (abCandidates.length != candidates.length || abApproved.length != candidates.length) {
			throw new IllegalArgumentException("The ballot's candidates do not match the ballot design's candidates.");
		}

		for (int i = 0; i < candidates.length; i++) {
			try {
				String cD = candidates[i];
				String cB = abCandidates[i];
				if (cD != cB && (cD == null || !cD.equalsIgnoreCase(cB))) {
					throw new IllegalArgumentException(
							"The ballot's candidates do not match the ballot design's candidates.");
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
	}

	/*
	 * There is one vote produced for each candidate. Each choice is either 1 for
	 * approve, or 0 for not-approve.
	 */
	@Override
	public int votesProducedPerBallot() {
		return numberOfCandidates();
	}

	@Override
	public int maxPossibleChoices() {
		return 2;
	}

	@Override
	public VoterSubmission decompose(Ballot ballot, int voterBlock, ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, String context, Map capabilities) throws IllegalArgumentException {

		if (!(ballot instanceof ApprovalBallot)) {
			throw new IllegalArgumentException("Incorrect kind of ballot.");
		}
		ApprovalBallot ab = (ApprovalBallot) ballot;
		boolean[] approved = ab.approved;
		if (candidates == null || approved == null || candidates.length != approved.length) {
			throw new IllegalArgumentException("Invalid ballot.");
		}
		if (key == null) {
			throw new IllegalArgumentException("No key supplied");
		}

		// add one vote for each candidate.
		VerifiableVote[] votes = new VerifiableVote[candidates.length];
		for (int i = 0; i < candidates.length; i++) {
			ElGamal1OfLReencryption encChoice = null;
			ElGamalReencryptFactor encChoiceFactor = null;
			try {
				int choice = approved[i] ? 1 : 0;
				encChoiceFactor = CryptoUtil.factory().generateElGamalReencryptFactor(key.getParams());
				encChoice = CryptoUtil.factory().elGamal1OfLReencrypt(key, ciphertexts, 2, choice, encChoiceFactor);
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}

			String desiredContext = context + CONTEXT_SUFFIX + i;
			VoteCapability c = null;
			try {
				Object cap = capabilities.get(desiredContext);
				c = (VoteCapability) cap;
			} catch (NullPointerException e) {
				throw new IllegalArgumentException("Not enough capabilities supplied");
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Not enough capabilities supplied");
			}
			if (c == null) {
				throw new IllegalArgumentException("No capability supplied for context " + desiredContext);
			}

			ElGamalReencryptFactor encCapFactor = null;
			ElGamalCiphertext encCap = null;
			ProofVote proofVote = null;
			try {
				encCapFactor = CryptoUtil.factory().generateElGamalReencryptFactor(key.getParams());
				encCap = CryptoUtil.factory().elGamalEncrypt(key, c, encCapFactor);
				proofVote = CryptoUtil.factory().constructProofVote(key.getParams(), encCap, encChoice, desiredContext,
						encCapFactor, encChoiceFactor);
			} catch (NullPointerException imposs) {
			}

			try {
				VerifiableVote v = new VerifiableVote(desiredContext, encChoice, encCap, proofVote);

				votes[i] = v; // RIGHT HERE THERE SHOULD BE A DECLASSIFICATION!
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		VoterSubmission vs = new VoterSubmission(voterBlock, votes);
		return vs;
	}

	@Override
	public void checkVoterSubmission(VoterSubmission vs, int startIndex, String context, CiphertextList ciphertexts,
			ElGamalPublicKey pubKey) throws IllegalArgumentException {
		if (candidates == null) {
			throw new IllegalArgumentException("Invalid ballot.");
		}
		if (vs == null) {
			throw new IllegalArgumentException("Invalid voter submission.");
		}
		if (pubKey == null) {
			throw new IllegalArgumentException("Invalid public Key");
		}
		try {
			for (int i = 0; i < votesProducedPerBallot(); i++) {
				VerifiableVote vv = vs.votes[startIndex + i];
				if (vv == null) {
					throw new IllegalArgumentException("Invalid verifiable vote.");
				}
				String vvcontext = vv.context;
				String desiredContext = (context == null ? "" : context) + CONTEXT_SUFFIX + i;
				if (!desiredContext.equals(vvcontext)) {
					throw new IllegalArgumentException("Vote did not have correct context");
				}

				if (!vv.verify(pubKey, ciphertexts, maxPossibleChoices())) {
					throw new IllegalArgumentException("Vote choice does not pass verification.");
				}
			}
			return;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		throw new IllegalArgumentException("Inappropriate vote for approval ballot");
	}

	@Override
	public void contextsNeeded(List l, String context) throws IllegalArgumentException {
		if (l == null)
			return;
		if (candidates == null) {
			throw new IllegalArgumentException("Invalid ballot.");
		}
		for (int i = 0; i < votesProducedPerBallot(); i++) {
			try {
				l.add((context == null ? "" : context) + CONTEXT_SUFFIX + i);
			} catch (ClassCastException unlikely) {
			}
		}
	}

	@Override
	public String nthContext(int n) {
		if (n >= 0 && n < votesProducedPerBallot())
			return CONTEXT_SUFFIX + n;
		return null;
	}

	@Override
	public void tally(String ctxt, ElGamalMsg m, String c, TallyState s, ElGamalParameters params)
			throws IllegalArgumentException {
		if (!(s instanceof ApprovalTallyState)) {
			throw new IllegalArgumentException("Incorrect tally state");
		}
		ApprovalTallyState ats = (ApprovalTallyState) s;

		// interpret the context and the message
		String desiredContext = (ctxt == null ? "" : ctxt) + CONTEXT_SUFFIX;
		if (c == null || desiredContext == null || !c.startsWith(desiredContext)) {
			throw new IllegalArgumentException("Incorrect context");
		}

		if (m == null) {
			throw new IllegalArgumentException("Null message");
		}
		try {
			String cand = c.substring(desiredContext.length());
			ats.record(Integer.parseInt(cand), (CryptoUtil.factory().elGamal1OfLValue(m, 2, params) != 0));
		} catch (CryptoException e) {
			throw new IllegalArgumentException("Invalid vote value");
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid index");
		} catch (NullPointerException imposs) {
			throw new IllegalArgumentException("impossible!");
		}

	}

	@Override
	public TallyState newTallyState() {
		return new ApprovalTallyState(this.candidates);
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(ApprovalBallotDesign.KIND);
		sb.print("</kind>");
		if (this.candidates != null) {
			sb.print("<size>");
			sb.print(this.candidates.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<candidates>");
		try {
			for (int i = 0; i < candidates.length; i++) {
				sb.print("<candidate>");
				Util.escapeString(candidates[i], sb);
				sb.print("</candidate>");
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</candidates>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static BallotDesign fromXML(Reader r) throws IllegalArgumentException, IOException {
		int size = Util.readSimpleIntTag(r, "size");
		Util.swallowTag(r, "candidates");
		String[] n = new String[size < 0 ? 0 : size];

		for (int i = 0; i < size; i++) {
			try {
				n[i] = Util.readSimpleTag(r, "candidate");
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "candidates");
		ApprovalBallotDesign b = new ApprovalBallotDesign(n);
		return b;
	}
}