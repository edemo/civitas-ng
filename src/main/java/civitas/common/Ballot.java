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
 * A Ballot is a representation of a voter's intentions. For example, in a
 * "single choice" race, there is a slate of candidates, of which the voter may
 * choose one; a ballot for a single choice race (a SingleChoiceBallot) records
 * which candidate the voter chose. A ballot is decomposed into
 * <code>Vote</code>s, which the voter submits to the bulletin board, and which
 * are used in the tabulation protocols.
 */
@Boilerplate
public abstract class Ballot {
	public abstract void toXML(PrintWriter sb);

	public final static String OPENING_TAG = "ballot";

	protected Ballot() {
	}

	/**
	 * Produce an appropriate Ballot from an XML representation. The super class
	 * needs to know about all subclasses.
	 */
	public static Ballot fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Ballot b = null;
		Util.swallowTag(r, OPENING_TAG);
		String kind = Util.unescapeString(Util.readSimpleTag(r, "kind"));
		if (kind == null) {
			throw new IOException("Unspecified kind");
		} else if (kind.equalsIgnoreCase(CondorcetBallotDesign.KIND)) {
			b = CondorcetBallot.fromXML(r);
		} else
			throw new IOException("Unknown ballot kind: " + kind);
		Util.swallowEndTag(r, OPENING_TAG);
		return b;
	}
}