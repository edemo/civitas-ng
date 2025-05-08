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
 * This class represents the results of an election. It also records who
 * computed the results. At the end of an election, each tabulation teller
 * computes the election results, and posts them to the bulletin board.
 */
public class ElectionResults implements XMLSerializable {
	public static final String META = "electionResults:";

	public static String metaForTeller(int tellerIndex) {
		return ElectionResults.META + computerForTeller(tellerIndex);
	}

	public static String computerForTeller(int tellerIndex) {
		return "Teller" + tellerIndex;
	}

	public final String computer;
	public final TallyStateFinal tally;

	public ElectionResults(String computer, TallyStateFinal tally) {
		this.computer = computer;
		this.tally = tally;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<electionResults>");

		sb.print("<computer>");
		Util.escapeString(this.computer, sb);
		sb.print("</computer>");
		if (this.tally != null) {
			this.tally.toXML(sb);
		}
		sb.print("</electionResults>");
	}

	public static ElectionResults fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "electionResults");
		String computer = Util.unescapeString(Util.readSimpleTag(r, "computer"));
		TallyStateFinal tally = TallyStateFinal.fromXML(r);
		Util.swallowEndTag(r, "electionResults");
		return new ElectionResults(computer, tally);
	}
}