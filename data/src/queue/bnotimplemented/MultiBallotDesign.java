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

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;

/**
 * This class is the ballot design of a multi-race. A multi-race consists of a
 * sequence of races. A MultiBallotDesign is the ballot designs for these races.
 */
public class MultiBallotDesign extends BallotDesign {
	public static final String KIND = "multi";

	public final BallotDesign[] designs;

	public MultiBallotDesign(BallotDesign[] designs) {
		super();
		BallotDesign[] ds = null;
		if (designs != null) {
			ds = designs.clone();
		}
		this.designs = ds;
	}

	@Override
	public void checkBallot(Ballot b) throws IllegalArgumentException {
		if (!(b instanceof MultiBallot)) {
			throw new IllegalArgumentException("Ballot is not a multi-ballot.");
		}

		MultiBallot mb = (MultiBallot) b;
		Ballot[] ballots = mb.ballots;

		if (designs == null || ballots == null) {
			throw new IllegalArgumentException("Missing the ballots.");
		}

		if (designs.length != ballots.length) {
			throw new IllegalArgumentException("Incorrect number of ballots");
		}

		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				Ballot bsub = ballots[i];
				if (bdsub == null)
					throw new IllegalArgumentException("Null ballot");
				bdsub.checkBallot(bsub);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
	}

	@Override
	public int votesProducedPerBallot() {
		// the sum of the votes produced by each sub-design
		int sum = 0;

		if (designs != null) {
			for (int i = 0; i < designs.length; i++) {
				try {
					BallotDesign bdsub = designs[i];
					sum += bdsub == null ? 0 : bdsub.votesProducedPerBallot();
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}
		return sum;
	}

	@Override
	public int maxPossibleChoices() {
		// the max of maxPossibleChoices produced by each sub-design
		int max = 0;

		if (designs != null) {
			for (int i = 0; i < designs.length; i++) {
				try {
					BallotDesign bdsub = designs[i];
					int n = bdsub == null ? 0 : bdsub.maxPossibleChoices();
					if (n > max)
						max = n;
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}
		return max;
	}

	@Override
	public VoterSubmission decompose(Ballot ballot, int voterBlock, ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, String context, Map capabilities) throws IllegalArgumentException {
		int size = votesProducedPerBallot();
		VerifiableVote[] votes = new VerifiableVote[size < 0 ? 0 : size];
		// go through each ballot in turn and decompose
		try {
			MultiBallot mb = null;
			if (ballot instanceof MultiBallot) {
				mb = (MultiBallot) ballot;
			} else {
				throw new IllegalArgumentException("incorrect ballot");
			}
			int votesCount = 0;
			for (int i = 0; i < mb.ballots.length; i++) {
				Ballot subBallot = mb.ballots[i];
				VoterSubmission subVS = designs[i].decompose(subBallot, voterBlock, key, ciphertexts, context + i + ":",
						capabilities);

				if (subVS.votes != null) {
					for (int j = 0; j < subVS.votes.length; j++) {
						votes[votesCount] = subVS.votes[j];
						votesCount++;
					}
				}
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
			throw new IllegalArgumentException("incorrect ballot");
		}
		VoterSubmission vs = new VoterSubmission(voterBlock, votes);
		return vs;
	}

	/**
	 * To check a multi-ballot voter submission, we check that each sub-ballot
	 * design is in turn correct.
	 */
	@Override
	public void checkVoterSubmission(VoterSubmission vs, int startIndex, String context, CiphertextList ciphertexts,
			ElGamalPublicKey pubKey) throws IllegalArgumentException {
		int start = startIndex;
		if (designs == null) {
			// no designs, so we swallow zero votes, and are thus correct...
			return;
		}

		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				if (bdsub == null)
					throw new IllegalArgumentException("Null design");
				bdsub.checkVoterSubmission(vs, start, context + i + ":", ciphertexts, pubKey);
				start += bdsub.votesProducedPerBallot();
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
	}

	@Override
	public void contextsNeeded(List l, String context) throws IllegalArgumentException {
		if (designs == null) {
			// no designs, so no contexts needed
			return;
		}

		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				if (bdsub == null)
					throw new IllegalArgumentException("Null design");
				bdsub.contextsNeeded(l, context + i + ":");
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
	}

	@Override
	public String nthContext(int n) {
		if (designs == null) {
			return null;
		}
		int s = 0;
		int t = 0;
		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				if (bdsub == null)
					return null;
				s = t;
				t += bdsub.votesProducedPerBallot();
				if (n >= s && n < t) {
					return i + ":" + bdsub.nthContext(n - s);
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return null;
	}

	@Override
	public void tally(String TestBase.getCtx()t, ElGamalMsg m, String c, TallyState s, ElGamalParameters params)
			throws IllegalArgumentException {
		if (!(s instanceof MultiTallyState)) {
			throw new IllegalArgumentException("Incorrect tally state");
		}
		MultiTallyState mts = (MultiTallyState) s;

		if (designs == null) {
			throw new IllegalArgumentException("No designs in this multi-ballot design");
		}

		boolean done = false;
		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				if (bdsub == null)
					throw new IllegalArgumentException("Null design");
				if (c != null && c.startsWith(TestBase.getCtx()t + i + ":")) {
					bdsub.tally(TestBase.getCtx()t + i + ":", m, c, mts.get(i), params);
					done = true;
					break;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		if (!done) {
			throw new IllegalArgumentException("Invalid context for vote: " + c);
		}

	}

	@Override
	public TallyState newTallyState() {
		if (designs == null)
			return null;

		TallyState[] states = new TallyState[designs.length];

		for (int i = 0; i < designs.length; i++) {
			try {
				BallotDesign bdsub = designs[i];
				if (bdsub != null) {
					states[i] = bdsub.newTallyState();
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return new MultiTallyState(states);
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(MultiBallotDesign.KIND);
		sb.print("</kind>");
		if (this.designs != null) {
			sb.print("<size>");
			sb.print(this.designs.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<designs>");
		try {
			for (int i = 0; i < designs.length; i++) {
				designs[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</designs>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static BallotDesign fromXML(Reader r) throws IllegalArgumentException, IOException {
		int size = Util.readSimpleIntTag(r, "size");
		Util.swallowTag(r, "designs");

		BallotDesign[] n = new BallotDesign[size < 0 ? 0 : size];

		for (int i = 0; i < size; i++) {
			try {
				n[i] = BallotDesign.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IOException("Too many ballot designs");
			}
		}

		MultiBallotDesign b = new MultiBallotDesign(n);

		Util.swallowEndTag(r, "designs");
		return b;
	}
}