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
 * This class is the ballot of an approval race. In an approval race, there is a
 * slate of candidates, and the voter indicates approval or disapproval of each
 * candidate. An ApprovalBallot records for each candidate whether the voter
 * approves or disapproves.
 */
public class ApprovalBallot extends Ballot {
	public String[] candidates;
	public boolean[] approved;

	public ApprovalBallot() {
		super();
		candidates = new String[0];
		approved = new boolean[0];
	}

	public void addCandidate(String c, boolean app) {
		try {
			String[] n = new String[candidates.length + 1];
			boolean[] b = new boolean[candidates.length + 1];
			for (int i = 0; i < candidates.length; i++) {
				try {
					n[i] = candidates[i];
					b[i] = approved[i];
				} catch (NullPointerException ignore) {
				} catch (ArrayIndexOutOfBoundsException ignore) {
				}
			}
			n[candidates.length] = c;
			b[candidates.length] = app;
			this.candidates = n;
			this.approved = b;
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(ApprovalBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<candidates>");
		try {
			for (int i = 0; i < candidates.length; i++) {
				sb.print("<candidate>");
				sb.print("<name>");
				Util.escapeString(candidates[i], sb);
				sb.print("</name>");
				sb.print("<approved>");
				sb.print(Boolean.toString(approved[i]));
				sb.print("</approved>");
				sb.print("</candidate>");
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</candidates>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static Ballot fromXML(Reader r) throws IllegalArgumentException, IOException {
		ApprovalBallot b = new ApprovalBallot();

		Util.swallowTag(r, "candidates");

		while (Util.isNextTag(r, "candidate")) {
			Util.swallowTag(r, "candidate");
			b.addCandidate(Util.unescapeString(Util.readSimpleTag(r, "name")),
					Util.readSimpleBooleanTag(r, "approved"));
			Util.swallowEndTag(r, "candidate");
		}

		Util.swallowEndTag(r, "candidates");
		return b;
	}
}