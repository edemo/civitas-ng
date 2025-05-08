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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;

/**
 * A BallotDesign describes a race. For example, in a "single choice" race,
 * there is a slate of candidates of which the voter may choose one; a ballot
 * design for a single choice race (a SingleChoiceBallotDesign) lists the
 * candidates on the slate.
 */
public abstract class BallotDesign implements XMLSerializable {
	public final static String OPENING_TAG = "ballotDesign";

	@Override
	public abstract void toXML(PrintWriter sb);

	/**
	 * Checks that the Ballot b is an acceptable ballot for this ballot design.
	 * Throws an IllegalArgumentException is this is not the case.
	 */
	public abstract void checkBallot(Ballot b) throws IllegalArgumentException;

	/**
	 * decompose the ballot into a VoterSubmission. The iterator gives a supply of
	 * capabilities, as a map from contexts for votes to
	 * <code>VoteCapability</code>s; an IllegalArgumentException is thrown if there
	 * are insufficient capabilities.
	 */
	public abstract VoterSubmission decompose(Ballot ballot, int voterBlock, ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, String context, Map capabilities) throws IllegalArgumentException;

	/**
	 * Checks that the votes in VoterSubmission vs correspond to an acceptable
	 * ballot for this ballot design. Throws an IllegalArgumentException is this is
	 * not the case.
	 */
	public final void checkVoterSubmission(VoterSubmission vs, String baseContext, CiphertextList ciphertexts,
			ElGamalPublicKey pubKey) throws IllegalArgumentException {
		checkVoterSubmission(vs, 0, baseContext, ciphertexts, pubKey);
	}

	/**
	 * Checks that the votes in VoterSubmission vs, from index
	 * <code>startIndex</code> correspond to an acceptable ballot for this ballot
	 * design. Throws an IllegalArgumentException is this is not the case.
	 */
	public abstract void checkVoterSubmission(VoterSubmission vs, int startIndex, String context,
			CiphertextList ciphertexts, ElGamalPublicKey pubKey) throws IllegalArgumentException;

	/**
	 * The number of votes in a <code>VoteSubmission</code> that will be produced by
	 * a single ballot for this ballot design.
	 */
	public abstract int votesProducedPerBallot();

	/**
	 * Returns a list of the contexts that will be used in a vote submission. The
	 * domain of the map capabilities given to the decompose method must contain
	 * these strings.
	 */
	public List contextsNeeded(String baseContext) throws IllegalArgumentException {
		List l = new ArrayList();
		contextsNeeded(l, baseContext);
		return l;
	}

	public abstract void contextsNeeded(List l, String context) throws IllegalArgumentException;

	public abstract String nthContext(int n);

	/**
	 * The maximum number of choices that a single verifiable vote will be out of.
	 * i.e., this determines the maximum value of L to be used in 1-of-L
	 * re-encryptions.
	 */
	public abstract int maxPossibleChoices();

	/**
	 * Tally the vote m with context c, given the parent context ctxt into the tally
	 * state s.
	 */
	public abstract void tally(String ctxt, ElGamalMsg m, String c, TallyState s, ElGamalParameters params)
			throws IllegalArgumentException;

	/**
	 * Construct a new tally state.
	 */
	public abstract TallyState newTallyState();

	/**
	 * Produce an appropriate BallotDesign from an XML representation. The super
	 * class needs to know about all subclasses.
	 */
	public static BallotDesign fromXML(Reader r) throws IllegalArgumentException, IOException {
		BallotDesign bd = null;

		Util.swallowTag(r, OPENING_TAG);
		String kind = Util.unescapeString(Util.readSimpleTag(r, "kind"));
		if (kind == null) {
			throw new IOException("Unspecified kind");
		} else if (kind.equalsIgnoreCase(ApprovalBallotDesign.KIND)) {
			bd = ApprovalBallotDesign.fromXML(r);
		} else if (kind.equalsIgnoreCase(SingleChoiceBallotDesign.KIND)) {
			bd = SingleChoiceBallotDesign.fromXML(r);
		} else if (kind.equalsIgnoreCase(CondorcetBallotDesign.KIND)) {
			bd = CondorcetBallotDesign.fromXML(r);
		} else if (kind.equalsIgnoreCase(MultiBallotDesign.KIND)) {
			bd = MultiBallotDesign.fromXML(r);
		} else
			throw new IOException("Unknown ballot kind: " + kind);
		Util.swallowEndTag(r, OPENING_TAG);
		return bd;
	}

}