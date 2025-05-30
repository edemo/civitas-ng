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
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.votecapability.VoteCapability;

/**
 * This class is the ballot design of a condorcet race. In a condorcet race,
 * there is a slate of candidates, and the voter indicates for each pair of
 * candidates i and j whether the voter prefers i to j, j to i, or does not
 * prefer one over the other. A CondorcetBallotDesign describes the slate of
 * candidates.
 */
public class CondorcetBallotDesign extends BallotDesign {
	public static final String KIND = "condorcet";
	private static final String CONTEXT_SUFFIX = "condorcet:";

	public static final byte VOTE_CHOICE_I_BEATS_J = 0;
	public static final byte VOTE_CHOICE_J_BEATS_I = 1;
	public static final byte VOTE_CHOICE_NEITHER_BEAT = 2; // i does not beat j, j
																													// does not beat i

	public final String[] candidates;

	/**
	 * If there are a total of k candidates, for pair (i,j), where 0<= i < j < k,
	 * what position in the matrix does the pair (i,j) occupy? The value p
	 * returned
	 * will satisfy 0 <= p < matrixSize(k).
	 */
	static int position(int i, int j, int k) {
		if (i == 0)
			return j - 1;
		return (j - i) + position(i - 1, k - 1, k);
	}

	/**
	 * If there are a total of k candidates, how many entries are needed in the
	 * matrix such that there is space for every pair (i,j), where 0<= i < j < k.
	 */
	static int matrixSize(int k) {
		return (int) ((k - 1) * (k / 2.0));
	}

	static String choiceToString(byte choice) {
		switch (choice) {
		case VOTE_CHOICE_I_BEATS_J:
			return "10";
		case VOTE_CHOICE_J_BEATS_I:
			return "01";
		case VOTE_CHOICE_NEITHER_BEAT:
			return "00";
		}
		return "INVALID";
	}

	static byte stringToChoice(String choice) {
		if ("10".equals(choice))
			return VOTE_CHOICE_I_BEATS_J;
		if ("01".equals(choice))
			return VOTE_CHOICE_J_BEATS_I;
		if ("00".equals(choice))
			return VOTE_CHOICE_NEITHER_BEAT;
		return -1;
	}

	public CondorcetBallotDesign(String[] candidates) {
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
		if (!(b instanceof CondorcetBallot)) {
			throw new IllegalArgumentException("Ballot is not an condorcet ballot.");
		}

		CondorcetBallot cb = (CondorcetBallot) b;

		byte[] cbMatrix = cb.matrix;

		if (cbMatrix == null || candidates == null) {
			throw new IllegalArgumentException("Missing slate.");
		}

		// check the number of candidates agree. Check the matrix is the right size,
		// and that something valid is recorded for each matrix entry.

		if (cb.k != candidates.length || cbMatrix.length != matrixSize(cb.k)) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}

		for (int i = 0; i < cb.k; i++) {
			for (int j = i + 1; j < cb.k; j++) {
				try {
					byte choice = cbMatrix[position(i, j, cb.k)];
					// check that choice is valid.
					if ("INVALID".equals(choiceToString(choice))) {
						throw new IllegalArgumentException(
								"Illegal choice for (" + i + "," + j + ")");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new IllegalArgumentException(
							"Matrix too small for (" + i + "," + j + ")");
				}

			}
		}
	}

	/*
	 * There is one vote produced for each (i,j) pair of candidates, where 0<=i<j<
	 * #candidates. Each choice is one of the 4 vote constants defined above.
	 */
	@Override
	public int

			votesProducedPerBallot() {
		return matrixSize(numberOfCandidates());
	}

	@Override
	public int

			maxPossibleChoices() {
		return 4;
	}

	@Override
	public VoterSubmission decompose(Ballot ballot, int voterBlock,
			ElGamalPublicKey key, ElGamalCiphertext[] ciphertexts, String context,
			Map capabilities) throws IllegalArgumentException {

		if (!(ballot instanceof CondorcetBallot)) {
			throw new IllegalArgumentException("Incorrect kind of ballot.");
		}
		if (key == null) {
			throw new IllegalArgumentException("No key supplied");
		}
		CondorcetBallot cb = (CondorcetBallot) ballot;
		byte[] cbMatrix = cb.matrix;
		if (cbMatrix == null || candidates == null) {
			throw new IllegalArgumentException("Missing slate.");
		}
		if (cb.k != candidates.length || cbMatrix.length != matrixSize(cb.k)) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}

		// add one vote for each matrix entry.
		int matrixSize = matrixSize(cb.k);
		VerifiableVote[] votes = new VerifiableVote[matrixSize < 0 ? 0
				: matrixSize];
		for (int i = 0; i < cb.k; i++) {
			for (int j = i + 1; j < cb.k; j++) {
				ElGamalReencryptFactor encChoiceFactor = null;
				ElGamal1OfLReencryption encChoice = null;
				int pos = position(i, j, cb.k);
				try {
					int choice = cbMatrix[position(i, j, cb.k)];
					encChoiceFactor = CryptoUtil.factory()
							.generateElGamalReencryptFactor(key.getParams());
					encChoice = CryptoUtil.factory().elGamal1OfLReencrypt(key,
							ciphertexts, 4, choice, encChoiceFactor);
				} catch (NullPointerException imposs) {
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}

				String desiredContext = context + CONTEXT_SUFFIX + i + ":" + j;
				VoteCapability c = null;
				try {
					c = (VoteCapability) capabilities.get(desiredContext);
				} catch (NullPointerException e) {
					throw new IllegalArgumentException(
							"Not enough capabilities supplied");
				} catch (ClassCastException e) {
					throw new IllegalArgumentException(
							"Not enough capabilities supplied");
				}
				if (c == null) {
					throw new IllegalArgumentException(
							"No capability supplied for context " + desiredContext);
				}

				ElGamalReencryptFactor encCapFactor = null;
				ElGamalCiphertext encCap = null;
				ProofVote proofVote = null;
				try {
					encCapFactor = CryptoUtil.factory()
							.generateElGamalReencryptFactor(key.getParams());
					encCap = CryptoUtil.factory().elGamalEncrypt(key, c, encCapFactor);
					proofVote = CryptoUtil.factory().constructProofVote(key.getParams(),
							encCap, encChoice, desiredContext, encCapFactor, encChoiceFactor);
				} catch (NullPointerException imposs) {
				}

				VerifiableVote v = new VerifiableVote(desiredContext, encChoice, encCap,
						proofVote);

				try {
					votes[pos] = v;
				} catch (ArrayIndexOutOfBoundsException imposs) {
					throw new IllegalArgumentException("Internal error");
				}
			}
		}
		VoterSubmission vs = new VoterSubmission(voterBlock, votes);
		return vs;
	}

	@Override
	public void checkVoterSubmission(VoterSubmission vs, int startIndex,
			String context, CiphertextList ciphertexts, ElGamalPublicKey pubKey)
			throws IllegalArgumentException {
		if (pubKey == null) {
			throw new IllegalArgumentException("Invalid public Key");
		}
		if (vs == null) {
			throw new IllegalArgumentException("Invalid voter submission.");
		}
		int k = numberOfCandidates();
		try {
			for (int i = 0; i < k; i++) {
				for (int j = i + 1; j < k; j++) {
					VerifiableVote vv = vs.votes[startIndex + position(i, j, k)];
					if (vv == null) {
						throw new IllegalArgumentException("Invalid verifiable vote.");
					}
					String vvcontext = vv.context;
					String desiredContext = (context == null ? "" : context)
							+ CONTEXT_SUFFIX + i + ":" + j;
					if (!desiredContext.equals(vvcontext)) {
						throw new IllegalArgumentException(
								"Vote did not have correct context");
					}

					if (!vv.verify(pubKey, ciphertexts, maxPossibleChoices())) {
						throw new IllegalArgumentException(
								"Vote choice odes not pass verification.");
					}
				}
			}
			return;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		throw new IllegalArgumentException(
				"Inappropriate vote for condorcet ballot");
	}

	@Override
	public void contextsNeeded(List l, String context)
			throws IllegalArgumentException {
		if (l == null)
			return;
		int k = numberOfCandidates();
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				try {
					l.add(
							(context == null ? "" : context) + CONTEXT_SUFFIX + i + ":" + j);
				} catch (ClassCastException unlikely) {
				}
			}
		}
	}

	@Override
	public String nthContext(int n) {
		int c = 0;
		int k = numberOfCandidates();
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				if (n == c) {
					return CONTEXT_SUFFIX + i + ":" + j;
				}
				c++;
			}
		}
		return null;
	}

	@Override
	public void tally(String ctxt, ElGamalMsg m, String c, TallyState s,
			ElGamalParameters params) throws IllegalArgumentException

	{
		if (!(s instanceof CondorcetTallyState)) {
			throw new IllegalArgumentException("Incorrect tally state");
		}
		CondorcetTallyState cts = (CondorcetTallyState) s;

		// interpret the context and the message
		String desiredContext = (ctxt == null ? "" : ctxt) + CONTEXT_SUFFIX;
		if (c == null || desiredContext == null || !c.startsWith(desiredContext)) {
			throw new IllegalArgumentException("Incorrect context");
		}

		if (m == null) {
			throw new IllegalArgumentException("Null message");
		}

		try {
			int choice = CryptoUtil.factory().elGamal1OfLValue(m, 4, params);
			String suffix = c.substring(desiredContext.length());
			// suffix is of the form "i:j". Find i and j.
			if (suffix != null) {
				int ind = suffix.indexOf(':');
				try {
					int i = Integer.parseInt(suffix.substring(0, ind));
					int j = Integer.parseInt(suffix.substring(ind + 1));
					if (choice == VOTE_CHOICE_I_BEATS_J) {
						cts.record(i, j);
					}
					if (choice == VOTE_CHOICE_J_BEATS_I) {
						cts.record(j, i);
					}

				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid context: " + suffix);
				}
			}
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
		return new CondorcetTallyState(candidates == null ? 0 : candidates.length);
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(CondorcetBallotDesign.KIND);
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
			for (String candidate : candidates) {
				sb.print("<candidate>");
				Util.escapeString(candidate, sb);
				sb.print("</candidate>");
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</candidates>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static BallotDesign fromXML(Reader r)
			throws IllegalArgumentException, IOException {
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
		CondorcetBallotDesign b = new CondorcetBallotDesign(n);

		return b;
	}
}