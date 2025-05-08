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
 * TallyStateFinal for a ApprovalBallotDesign.
 */
public class ApprovalTallyStateFinal extends TallyStateFinal {
	public final String[] candidates;
	private final int[] approvals;
	private final int[] disapprovals;

	ApprovalTallyStateFinal(String[] candidates, int[] approvals, int[] disapprovals) {
		super();
		int[] as = null;
		int[] ds = null;
		if (approvals != null)
			as = approvals.clone();
		if (disapprovals != null)
			ds = disapprovals.clone();
		this.approvals = as;
		this.disapprovals = ds;
		this.candidates = candidates;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(ApprovalBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<count>");
		sb.print(candidates == null ? 0 : candidates.length);
		sb.print("</count>");

		sb.print("<approvalCounts>");
		for (int i = 0; candidates != null && i < candidates.length; i++) {
			sb.print("<candidate>");
			try {
				sb.print("<name>");
				sb.print(candidates[i]);
				sb.print("</name>");
				sb.print("<approvals>");
				sb.print(approvals[i]);
				sb.print("</approvals>");
				sb.print("<disapprovals>");
				sb.print(disapprovals[i]);
				sb.print("</disapprovals>");
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
			sb.print("</candidate>");
		}
		sb.print("</approvalCounts>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static TallyStateFinal fromXML(Reader r) throws IllegalArgumentException, IOException {
		int count = Util.readSimpleIntTag(r, "count");

		String[] candidates = new String[count < 0 ? 0 : count];
		int[] approvals = new int[count < 0 ? 0 : count];
		int[] disapprovals = new int[count < 0 ? 0 : count];

		Util.swallowTag(r, "approvalCounts");
		for (int i = 0; i < count; i++) {
			Util.swallowTag(r, "candidate");
			try {
				candidates[i] = Util.readSimpleTag(r, "name");
				approvals[i] = Util.readSimpleIntTag(r, "approvals");
				disapprovals[i] = Util.readSimpleIntTag(r, "disapprovals");
			} catch (ArrayIndexOutOfBoundsException imposs) {
			} catch (NullPointerException imposs) {
			}
			Util.swallowEndTag(r, "candidate");
		}

		Util.swallowEndTag(r, "approvalCounts");
		ApprovalTallyStateFinal b = new ApprovalTallyStateFinal(candidates.clone(), approvals, disapprovals);

		return b;
	}
}