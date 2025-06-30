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
 * TallyStateFinal for a MultiballotDesign.
 */
public class MultiTallyStateFinal extends TallyStateFinal {
	private final TallyStateFinal[] states;

	MultiTallyStateFinal(TallyStateFinal[] states) {
		super();
		TallyStateFinal[] ss = null;
		if (states != null)
			ss = states.clone();
		this.states = ss;
	}

	TallyStateFinal get(int i) {
		if (states != null && i >= 0 && i < states.length) {
			try {
				return states[i];
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
		sb.print("<kind>");
		sb.print(MultiBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<count>");
		sb.print(states == null ? 0 : states.length);
		sb.print("</count>");
		sb.print("<tallyStates>");
		try {
			for (int i = 0; i < states.length; i++) {
				states[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</tallyStates>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static TallyStateFinal fromXML(Reader r) throws IllegalArgumentException, IOException {
		int count = Util.readSimpleIntTag(r, "count");

		Util.swallowTag(r, "tallyStates");
		TallyStateFinal[] states = new TallyStateFinal[count < 0 ? 0 : count];
		for (int i = 0; i < count; i++) {
			try {
				states[i] = TallyStateFinal.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "tallyStates");
		return new MultiTallyStateFinal(states);
	}
}