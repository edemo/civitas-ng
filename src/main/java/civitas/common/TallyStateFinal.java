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

import civitas.util.Boilerplate;

/**
 * This class is the super class of all final tally states. A final tally state
 * is very similar to a tally state, not it is not mutable.
 */
@Boilerplate
public abstract class TallyStateFinal implements XMLSerializable {
	@Override
	public abstract void toXML(PrintWriter sb);

	public final static String OPENING_TAG = "tallyState";

	public void toXML(PrintWriter[] sb) {
		toXML(sb);
	}

	public static TallyStateFinal fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		TallyStateFinal b = null;
		Util.swallowTag(r, OPENING_TAG);
		String kind = Util.unescapeString(Util.readSimpleTag(r, "kind"));
		if (kind == null) {
			throw new IOException("Unspecified kind");
		} else if (kind.equalsIgnoreCase(CondorcetBallotDesign.KIND)) {
			b = CondorcetTallyStateFinal.fromXML(r);
		} else
			throw new IOException("Unknown ballot kind: " + kind);
		Util.swallowEndTag(r, OPENING_TAG);
		return b;
	}
}