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
 * This class is the ballot design of a single choice race. In a single choice
 * race, there is a slate of candidates, and the voter selects one candidate. A
 * SingleChoiceBallotDesign describes the candidates in the race.
 */
public class SingleChoiceBallotDesign extends BallotDesign {

	public static final String KIND = "singleChoice";

	public final String[] candidates;

	public SingleChoiceBallotDesign(String[] candidates) {
		super();
		String[] cs;
		if (candidates != null) {
			cs = candidates.clone();
		} else {
			cs = new String[0];
		}
		this.candidates = cs;
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

	public int numberOfCandidates() {
		if (candidates == null)
			return 0;
		return candidates.length;
	}

	@Override
	public void checkBallot(Ballot b) throws IllegalArgumentException {
		if (!(b instanceof SingleChoiceBallot)) {
			throw new IllegalArgumentException("Ballot is not a single choice ballot.");
		}

		SingleChoiceBallot scb = (SingleChoiceBallot) b;

		if (candidates == null) {
			throw new IllegalArgumentException("Missing slate.");
		}

		if (indexOfCandidate(scb.candidate) < 0) {
			throw new IllegalArgumentException("No valid candidate selected");
		}
	}

	private static final String CONTEXT_SUFFIX = "single";

	@Override
	public int votesProducedPerBallot() {
		// a single vote only is produced by the decompose method.
		return 1;
	}

	@Override
	public int maxPossibleChoices() {
		return numberOfCandidates();
	}

	@Override
	public VoterSubmission decompose(Ballot ballot, int voterBlock, ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, String context, Map capabilities) throws IllegalArgumentException {
		// the decomposition is just a single vote! very straightforward...

		// convert the candidate name into an index...
		int choice = -1;
		if (ballot instanceof SingleChoiceBallot) {
			SingleChoiceBallot scb = (SingleChoiceBallot) ballot;
			choice = this.indexOfCandidate(scb.candidate);
		}
		if (choice == -1)
			throw new IllegalArgumentException("Trying to decompose a bad choice!");
		if (key == null) {
			throw new IllegalArgumentException("No key supplied");
		}

		ElGamalReencryptFactor encChoiceFactor = null;
		ElGamal1OfLReencryption encChoice = null;
		try {
			encChoiceFactor = CryptoUtil.factory().generateElGamalReencryptFactor(key.getParams());
			encChoice = CryptoUtil.factory().elGamal1OfLReencrypt(key, ciphertexts, numberOfCandidates(), choice,
					encChoiceFactor);
		} catch (NullPointerException imposs) {
		}

		String desiredContext = context + CONTEXT_SUFFIX;
		VoteCapability c = null;
		try {
			c = (VoteCapability) capabilities.get(desiredContext);
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

		VerifiableVote v = new VerifiableVote(desiredContext, encChoice, encCap, proofVote);

		VoterSubmission vs = new VoterSubmission(voterBlock, new VerifiableVote[] { v });
		return vs;
	}

	/**
	 * To check a single choice voter submission, we check that the context is
	 * correct.
	 */
	@Override
	public void checkVoterSubmission(VoterSubmission vs, int startIndex, String context, CiphertextList ciphertexts,
			ElGamalPublicKey pubKey) throws IllegalArgumentException {
		try {
			if (pubKey == null) {
				throw new IllegalArgumentException("Invalid public Key");
			}
			VerifiableVote vv = vs.votes[startIndex];
			String vvcontext = vv.context;
			String desiredContext = (context == null ? "" : context) + CONTEXT_SUFFIX;
			if (!desiredContext.equals(vvcontext)) {
				throw new IllegalArgumentException("Vote did not have correct context");
			}

			if (!vv.verify(pubKey, ciphertexts, maxPossibleChoices())) {
				throw new IllegalArgumentException("Vote choice does not pass verification.");
			}

			return;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		throw new IllegalArgumentException("No approriate vote for single choice ballot");

	}

	@Override
	public void contextsNeeded(List l, String context) throws IllegalArgumentException {
		if (l == null)
			return;
		try {
			l.add((context == null ? "" : context) + CONTEXT_SUFFIX);
		} catch (ClassCastException unlikely) {
		}

	}

	@Override
	public String nthContext(int n) {
		if (n == 0)
			return CONTEXT_SUFFIX;
		return null;
	}

	@Override
	public void tally(String ctxt, ElGamalMsg m, String c, TallyState s, ElGamalParameters params)
			throws IllegalArgumentException {
		if (!(s instanceof SingleChoiceTallyState)) {
			throw new IllegalArgumentException("Incorrect tally state");
		}
		SingleChoiceTallyState scts = (SingleChoiceTallyState) s;

		String desiredContext = (ctxt == null ? "" : ctxt) + CONTEXT_SUFFIX;
		if (desiredContext == null || !desiredContext.equals(c)) {
			throw new IllegalArgumentException("Incorrect context");
		}
		if (m == null) {
			throw new IllegalArgumentException("Null message");
		}
		try {
			scts.increment(CryptoUtil.factory().elGamal1OfLValue(m, numberOfCandidates(), params));
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
		return new SingleChoiceTallyState(candidates);
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(SingleChoiceBallotDesign.KIND);
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
				n[i] = Util.unescapeString(Util.readSimpleTag(r, "candidate"));
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "candidates");
		SingleChoiceBallotDesign b = new SingleChoiceBallotDesign(n);
		return b;
	}
}