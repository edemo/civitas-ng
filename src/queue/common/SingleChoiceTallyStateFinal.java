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
 * TallyStateFinal for a SingleChoiceballotDesign.
 */
public class SingleChoiceTallyStateFinal extends TallyStateFinal {
	public final int[] counts;
	public final String[] candidates;

	SingleChoiceTallyStateFinal(String[] candidates, int[] counts) {
		super();
		int[] cs = null;
		if (counts != null)
			cs = counts.clone();
		this.counts = cs;
		this.candidates = candidates;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(SingleChoiceBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<count>");
		sb.print(counts == null ? 0 : counts.length);
		sb.print("</count>");

		sb.print("<candidateCounts>");
		try {
			for (int i = 0; i < counts.length; i++) {
				sb.print("<candidate>");
				sb.print("<name>");
				sb.print(candidates[i]);
				sb.print("</name>");
				sb.print("<tally>");
				sb.print(counts[i]);
				sb.print("</tally>");
				sb.print("</candidate>");
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</candidateCounts>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static TallyStateFinal fromXML(Reader r) throws IllegalArgumentException, IOException {
		int count = Util.readSimpleIntTag(r, "count");

		String[] candidates = new String[count < 0 ? 0 : count];
		int[] counts = new int[count < 0 ? 0 : count];

		Util.swallowTag(r, "candidateCounts");
		for (int i = 0; i < count; i++) {
			try {
				Util.swallowTag(r, "candidate");
				candidates[i] = Util.readSimpleTag(r, "name");
				counts[i] = Util.readSimpleIntTag(r, "tally");
				Util.swallowEndTag(r, "candidate");
			} catch (ArrayIndexOutOfBoundsException imposs) {
			} catch (NullPointerException imposs) {
			}
		}

		Util.swallowEndTag(r, "candidateCounts");
		SingleChoiceTallyStateFinal b = new SingleChoiceTallyStateFinal(candidates.clone(), counts);
		return b;
	}
}