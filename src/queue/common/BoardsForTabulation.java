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
 * This class lists the BoardClosedContentCommitments that are approved for
 * tabulation. A BoardsForTabulation message is posted by the election
 * supervisor at the start of tabulation, to indicate to the tabulation teller
 * what ballot boxes are to be used for tabulation. (Note: Voter ballot boxes
 * were previously known as voter bulletin boards, hence the name of this
 * class.)
 */
public class BoardsForTabulation implements XMLSerializable {
	public final BoardClosedContentCommitment[] contentComs;

	public static final String META = "boardsForTabulation";
	public static final String OPENING_TAG = "boardsForTabulation";

	public BoardsForTabulation(BoardClosedContentCommitment[] contentComs) {
		BoardClosedContentCommitment[] cc = null;
		if (contentComs != null) {
			cc = contentComs.clone();
		}

		this.contentComs = cc;
	}

	public BoardClosedContentCommitment contentCommitmentForBoard(int boardIndex) {
		if (contentComs == null)
			return null;
		for (int i = 0; i < contentComs.length; i++) {
			try {
				BoardClosedContentCommitment cc = contentComs[i];
				if (cc != null && cc.boardIndex == boardIndex) {
					return cc;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return null;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");

		if (this.contentComs != null) {
			sb.print("<size>");
			sb.print(this.contentComs.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<contentComs>");
		try {
			for (int i = 0; i < contentComs.length; i++) {
				contentComs[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</contentComs>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static BoardsForTabulation fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);

		int ccSize = Util.readSimpleIntTag(r, "size");
		BoardClosedContentCommitment[] n = new BoardClosedContentCommitment[ccSize < 0 ? 0 : ccSize];
		Util.swallowTag(r, "contentComs");
		for (int i = 0; i < ccSize; i++) {
			try {
				n[i] = BoardClosedContentCommitment.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		Util.swallowEndTag(r, "contentComs");

		Util.swallowEndTag(r, OPENING_TAG);

		BoardsForTabulation bt = new BoardsForTabulation(n);
		return bt;
	}
}