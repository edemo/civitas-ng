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
 * This class is the ballot of a single choice race. In a single choice race,
 * there is a slate of candidates, and the voter selects one candidate. A
 * SingleChoiceBallot records which candidate the voter chose.
 */
public class SingleChoiceBallot extends Ballot {
	public final String candidate;

	public SingleChoiceBallot(String candidate) {
		super();
		this.candidate = candidate;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(SingleChoiceBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<candidate>");
		Util.escapeString(candidate, sb);
		sb.print("</candidate>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static Ballot fromXML(Reader r) throws IllegalArgumentException, IOException {
		return new SingleChoiceBallot(Util.unescapeString(Util.readSimpleTag(r, "candidate")));
	}
}