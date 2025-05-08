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
 * An election abandonment states that a teller has abandoned the election, and
 * gives the reason why.
 */
public class ElectionAbandonment implements XMLSerializable {
	public final static String META = "electionAbandonment";

	public final int tellerIndex;
	public final boolean isTabulationTeller;

	public final String reason;

	public ElectionAbandonment(int tellerIndex, boolean isTabulationTeller, String reason) {
		this.tellerIndex = tellerIndex;
		this.isTabulationTeller = isTabulationTeller;
		this.reason = reason;
	}

	public String reporter() {
		if (isTabulationTeller) {
			return "tabulation teller " + tellerIndex;
		}
		return "unknown entity";
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<electionAbandonment>");
		sb.print("<tellerIndex>");
		sb.print(tellerIndex);
		sb.print("</tellerIndex>");
		sb.print("<isTabulationTeller>");
		sb.print(isTabulationTeller);
		sb.print("</isTabulationTeller>");
		sb.print("<reason>");
		Util.escapeString(reason, sb);
		sb.print("</reason>");

		sb.print("</electionAbandonment>");
	}

	public static ElectionAbandonment fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "electionAbandonment");
		int ti = Util.readSimpleIntTag(r, "tellerIndex");
		boolean isTab = Util.readSimpleBooleanTag(r, "isTabulationTeller");
		String reason = Util.unescapeString(Util.readSimpleTag(r, "reason"));
		Util.swallowEndTag(r, "electionAbandonment");

		return new ElectionAbandonment(ti, isTab, reason);
	}
}