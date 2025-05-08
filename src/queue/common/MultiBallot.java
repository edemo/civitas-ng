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
 * This class is the ballot of a multi-race. A multi-race consists of a sequence
 * of races. A MultiBallot records the sequence of ballots for these races.
 */
public class MultiBallot extends Ballot {
	public Ballot[] ballots;

	public MultiBallot() {
		super();
		ballots = new Ballot[0];
	}

	public void addBallot(Ballot bd) {
		try {
			Ballot[] n = new Ballot[ballots.length + 1];
			for (int i = 0; i < ballots.length; i++) {
				n[i] = ballots[i];
			}
			n[ballots.length] = bd;
			ballots = n;
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
		sb.print(MultiBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<ballots>");
		try {
			for (int i = 0; i < ballots.length; i++) {
				ballots[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</ballots>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static Ballot fromXML(Reader r) throws IllegalArgumentException, IOException {
		MultiBallot b = new MultiBallot();

		Util.swallowTag(r, "ballots");

		while (Util.isNextTag(r, Ballot.OPENING_TAG)) {
			b.addBallot(Ballot.fromXML(r));
		}

		Util.swallowEndTag(r, "ballots");
		return b;
	}
}