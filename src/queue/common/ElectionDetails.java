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

import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.PublicKey;

/**
 * This class contains the basic details of an election.
 */
public class ElectionDetails implements XMLSerializable {
	public final ElectionID electionID; // is also the BB id
	public final PublicKey supervisor;
	public final PublicKey registrar;
	public final String name;
	public final String description;
	public final String version; // the version of Civitas this election is compatible with
	public final BallotDesign ballotDesign; // the ballot design

	/*
	 * Start, stop, and finalize times are advisory only, and are non-binding. They
	 * indicate the supervisor's intention as to when the voting/registration period
	 * will start and stop, and when the final results will be announced by.
	 */
	public final String startTime;
	public final String stopTime;
	public final String finalizeTime;

	/*
	 * Security parameters
	 */
	public final ElGamalParameters elGamalParameters; // the parameters used for all ElGamal encryption in this election
	public final int sharedKeyLength; // length in bits of shared keys
	public final int nonceLength; // length in bits of nonces to be used.
	public final int voterAnonymityParam; // the expected number of voters that will appear in each block

	public final static String META = "electionDetails";

	public ElectionDetails(ElectionID electionID, PublicKey supervisor, PublicKey registrar, String name,
			String description, String version, BallotDesign ballotDesign, String startTime, String stopTime,
			String finalizeTime, ElGamalParameters elGamalParameters, int sharedKeyLength, int nonceLength,
			int voterAnonymityParam) {
		this.electionID = electionID;
		this.supervisor = supervisor;
		this.registrar = registrar;
		this.name = name;
		this.description = description;
		this.version = version;
		this.ballotDesign = ballotDesign;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.finalizeTime = finalizeTime;
		this.elGamalParameters = elGamalParameters;
		this.sharedKeyLength = sharedKeyLength;
		this.nonceLength = nonceLength;
		this.voterAnonymityParam = voterAnonymityParam;
	}

	/**
	 * Convert the election details into an XML string suitable for posting to the
	 * bulletin board.
	 */
	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<electionDetails>");
		if (this.electionID != null) {
			this.electionID.toXML(sb);
		}

		sb.print("<supervisorPK>");
		if (supervisor != null) {
			supervisor.toXML(sb);
		}
		sb.print("</supervisorPK>");
		sb.print("<registrarPK>");
		if (registrar != null) {
			registrar.toXML(sb);
		}
		sb.print("</registrarPK>");
		sb.print("<name>");
		Util.escapeString(this.name, sb);
		sb.print("</name>");
		sb.print("<description>");
		Util.escapeString(this.description, sb);
		sb.print("</description>");
		sb.print("<version>");
		Util.escapeString(this.version, sb);
		sb.print("</version>");
		if (this.ballotDesign != null) {
			this.ballotDesign.toXML(sb);
		}
		sb.print("<startTime>");
		Util.escapeString(this.startTime, sb);
		sb.print("</startTime>");
		sb.print("<stopTime>");
		Util.escapeString(this.stopTime, sb);
		sb.print("</stopTime>");
		sb.print("<finalizeTime>");
		Util.escapeString(this.finalizeTime, sb);
		sb.print("</finalizeTime>");

		if (this.elGamalParameters != null) {
			this.elGamalParameters.toXML(sb);
		}

		sb.print("<sharedKeyLength>");
		sb.print(sharedKeyLength);
		sb.print("</sharedKeyLength>");
		sb.print("<nonceLength>");
		sb.print(nonceLength);
		sb.print("</nonceLength>");
		sb.print("<voterAnonymityParam>");
		sb.print(voterAnonymityParam);
		sb.print("</voterAnonymityParam>");
		sb.print("</electionDetails>");
	}

	/**
	 * Convert a bulletin board message into an election details object.
	 * 
	 * @throws IllegalArgumentException of the xml is not appropriate for the
	 *                                  object.
	 */
	public static ElectionDetails fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "electionDetails");
		ElectionID electionID = ElectionID.fromXML(r);
		Util.swallowTag(r, "supervisorPK");
		PublicKey supPK = null;
		if (Util.isNextTag(r, PublicKey.OPENING_TAG)) {
			try {
				supPK = CryptoUtil.factory().publicKeyFromXML(r);
			} catch (NullPointerException imposs) {
			}
		}
		Util.swallowEndTag(r, "supervisorPK");

		Util.swallowTag(r, "registrarPK");
		PublicKey regPK = null;
		if (Util.isNextTag(r, PublicKey.OPENING_TAG)) {
			try {
				regPK = CryptoUtil.factory().publicKeyFromXML(r);
			} catch (NullPointerException imposs) {
			}
		}
		Util.swallowEndTag(r, "registrarPK");

		String name = Util.unescapeString(Util.readSimpleTag(r, "name"));
		String description = Util.unescapeString(Util.readSimpleTag(r, "description"));
		String version = Util.unescapeString(Util.readSimpleTag(r, "version"));

		BallotDesign ballotDesign = null;
		if (Util.isNextTag(r, "ballotDesign")) {
			ballotDesign = BallotDesign.fromXML(r);
		}

		String startTime = Util.unescapeString(Util.readSimpleTag(r, "startTime"));
		String stopTime = Util.unescapeString(Util.readSimpleTag(r, "stopTime"));
		String finalizeTime = Util.unescapeString(Util.readSimpleTag(r, "finalizeTime"));

		ElGamalParameters params = null;
		if (Util.isNextTag(r, "elGamalParameters")) {
			try {
				params = CryptoUtil.factory().elGamalParametersFromXML(r);
			} catch (NullPointerException imposs) {
			}
		}

		int sharedKeyLength = Util.readSimpleIntTag(r, "sharedKeyLength");
		int nonceLength = Util.readSimpleIntTag(r, "nonceLength");
		int voterAnonymityParam = Util.readSimpleIntTag(r, "voterAnonymityParam");
		Util.swallowEndTag(r, "electionDetails");

		return new ElectionDetails(electionID, supPK, regPK, name, description, version, ballotDesign, startTime,
				stopTime, finalizeTime, params, sharedKeyLength, nonceLength, voterAnonymityParam);
	}

	public int voterAnonymityParam() {
		return voterAnonymityParam;
	}

	public int voterBlockForBlock(int block) {
		if (ballotDesign != null) {
			int numberContexts = ballotDesign.votesProducedPerBallot();
			try {
				return block / numberContexts;
			} catch (ArithmeticException unlikely) {
			}
		}
		return 0;

	}

	public int contextForBlock(int block) {
		if (ballotDesign != null) {
			int numberContexts = ballotDesign.votesProducedPerBallot();
			try {
				return block % numberContexts;
			} catch (ArithmeticException unlikely) {
			}
		}
		return 0;

	}

	public String blockName(int block) {
		int voterBlock = voterBlockForBlock(block);
		int context = contextForBlock(block);
		if (ballotDesign != null) {
			return "voterBlock-" + voterBlock + "-context-" + ballotDesign.nthContext(context);
		}
		return "block" + block;
	}

	/**
	 * Does the voter have any votes in the block <code>block</code>?
	 */
	public boolean inBlock(VoterSubmission voter, int block) {
		int voterBlock = voterBlockForBlock(block);
		if (voter != null) {
			return voterBlock == voter.voterBlock;
		}

		return false;
	}

	/**
	 * Does the voter <code>voter</code> have any votes in the block
	 * <code>block</code>?
	 */
	public boolean inBlock(VoterEncCapabilities voter, int block) {
		int voterBlock = voterBlockForBlock(block);
		if (voter != null) {
			return voterBlock == voter.voterBlock;
		}

		return false;
	}

	/**
	 * Is a vote with context <vode>context</code> submitted by <code>voter</code>
	 * in the block <code>block</code>?
	 */
	public boolean inBlock(VoterEncCapabilities voter, String context, int block) {
		int voterBlock = voterBlockForBlock(block);
		int contextN = contextForBlock(block);
		if (ballotDesign != null && voter != null) {
			return voterBlock == voter.voterBlock && context != null
					&& context.equals(ballotDesign.nthContext(contextN));
		}

		return false;
	}

	/**
	 * Is a vote with context <vode>context</code> submitted by <code>voter</code>
	 * in the block <code>block</code>?
	 */
	public boolean inBlock(VoterSubmission voter, String context, int block) {
		int voterBlock = voterBlockForBlock(block);
		int contextN = contextForBlock(block);
		if (ballotDesign != null && voter != null) {
			return voterBlock == voter.voterBlock && context != null
					&& context.equals(baseContext(voterBlock) + ballotDesign.nthContext(contextN));
		}

		return false;
	}

	/**
	 * Return the initial context for the voter block given, which is simply the
	 * election ID concatenated with the voter block.
	 */
	public String baseContext(int voterBlock) {
		return (this.electionID == null ? "0" : this.electionID.toString()) + ':' + voterBlock + ':';
	}
}