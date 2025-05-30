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

import civitas.util.Use;

/**
 * A submission made by a voter to the ballot box. It is a collection of
 * VeriableVotes, and the voter block that the voter belings in.
 */
public class VoterSubmission implements XMLSerializable {
	public final static String meta(int voterBlock) {
		return "voterSubmission-voterBlock" + voterBlock;
	}

	/**
	 * for the purposes of blocking, voters are divided into blocks, according to
	 * the security parameters of the election details.
	 */
	public final int voterBlock;

	/**
	 * Verifiable votes, which are the decomposition of the voter's ballot.
	 */
	public final VerifiableVote[] votes;

	public VoterSubmission(int voterBlock, VerifiableVote[] votes) {
		super();
		VerifiableVote[] vs = null;
		if (votes != null) {
			vs = votes.clone();
		}
		this.votes = vs;
		this.voterBlock = voterBlock;
	}

	public int size() {
		return votes == null ? 0 : votes.length;
	}

	@Override
	public String toString() {
		return "voter-submission";
//        StringBuffer[ ] sb = new StringBuffer[ ]();
//        this.toXML(new label  , sb);
//        return sb.toString();
	}

	@Override
	public int hashCode() {
		int hash = voterBlock;
		try {
			for (int i = 0; i < this.votes.length && i < 1; i++) {
				// just use the first enc capability as the hash code.
				hash ^= this.votes[i].encCapability.hashCode();
			}
		} catch (ArrayIndexOutOfBoundsException imposs) {
		} catch (NullPointerException ignore) {
		}
		return hash;
	}

	public boolean equals(Comparable obj) {
		if (obj instanceof VoterSubmission) {
			VoterSubmission that = (VoterSubmission) obj;
			if (this.voterBlock == that.voterBlock && this.size() == that.size()) {
				if (this.votes != null) {
					for (int i = 0; i < this.votes.length; i++) {
						try {
							if (!this.votes[i].equals(that.votes[i])) {
								return false;
							}
						} catch (ArrayIndexOutOfBoundsException imposs) {
							return false;
						} catch (NullPointerException imposs) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Use
	VerifiableVoteToXML verifiableVoteToXML;

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<voterSubmission>");
		sb.print("<voterBlock>");
		sb.print(this.voterBlock);
		sb.print("</voterBlock>");
		if (this.votes != null) {
			sb.print("<size>");
			sb.print(this.votes.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<votes>");
		try {
			for (VerifiableVote vote : votes) {
				verifiableVoteToXML.apply(vote, sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</votes>");
		sb.print("</voterSubmission>");
	}

	public static VoterSubmission fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "voterSubmission");

		int voterBlock = Util.readSimpleIntTag(r, "voterBlock");
		int size = Util.readSimpleIntTag(r, "size");

		Util.swallowTag(r, "votes");
		VerifiableVote[] n = new VerifiableVote[size < 0 ? 0 : size];

		for (int i = 0; i < size; i++) {
			try {
				n[i] = VerifiableVote.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IOException("Too many verifiable votes");
			}
		}

		Util.swallowEndTag(r, "votes");
		Util.swallowEndTag(r, "voterSubmission");
		VoterSubmission vs = new VoterSubmission(voterBlock, n);
		return vs;
	}
}