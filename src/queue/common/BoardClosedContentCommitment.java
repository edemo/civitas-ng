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

/**
 * This class represents a mesasge posted by a voter ballot box to commit to the
 * contents of the ballot box at the end of the voting phase. (Note: Voter
 * ballot boxes were previously known as voter bulletin boards, hence the name
 * of this class.)
 */
public class BoardClosedContentCommitment implements XMLSerializable {
	public final static String META = "boardContents";
	public final static String OPENING_TAG = "boardContents";

	public final ElectionID electionID;
	public final int boardIndex;

	public final String[] voterBlockContentHash; // hashes of the board, per voter block.

	public BoardClosedContentCommitment(ElectionID electionID, int boardIndex, String[] voterBlockContentHash) {
		this.electionID = electionID;
		this.boardIndex = boardIndex;
		String[] ch;
		if (voterBlockContentHash != null) {
			ch = voterBlockContentHash.clone();
		} else {
			ch = new String[0];
		}
		this.voterBlockContentHash = ch;
	}

	public String contentHash(int voterBlock) {
		try {
			return voterBlockContentHash[voterBlock];
		} catch (NullPointerException ignore) {
		} catch (ArrayIndexOutOfBoundsException ignore) {
		}
		return null;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		if (this.electionID != null) {
			this.electionID.toXML(sb);
		}

		sb.print("<index>" + boardIndex + "</index>");
		if (this.voterBlockContentHash != null) {
			sb.print("<size>");
			sb.print(this.voterBlockContentHash.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<hashes>");
		try {
			for (int i = 0; i < voterBlockContentHash.length; i++) {
				sb.print("<hash>");
				Util.escapeString(voterBlockContentHash[i], sb);
				sb.print("</hash>");
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</hashes>");

		sb.print("</" + OPENING_TAG + ">");
	}

	public static BoardClosedContentCommitment fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);
		ElectionID electionID = ElectionID.fromXML(r);
		int index = Util.readSimpleIntTag(r, "index");
		int size = Util.readSimpleIntTag(r, "size");
		Util.swallowTag(r, "hashes");
		String[] n = new String[size < 0 ? 0 : size];

		for (int i = 0; i < size; i++) {
			try {
				n[i] = Util.unescapeString(Util.readSimpleTag(r, "hash"));
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "hashes");
		Util.swallowEndTag(r, OPENING_TAG);

		return new BoardClosedContentCommitment(electionID, index, n);
	}
}