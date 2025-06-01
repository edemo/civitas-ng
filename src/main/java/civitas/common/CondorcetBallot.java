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
 * This class is the ballot of a condorcet race. In a condorcet race, there is a
 * slate of candidates, and the voter indicates for each pair of candidates i
 * and j whether the voter prefers i to j, j to i, or does not prefer one over
 * the other. A CondorcetBallot records a voter's preferences.
 */
@Boilerplate
public class CondorcetBallot extends Ballot {
	/**
	 * The number of candidates
	 */
	public final int k;

	/**
	 * Representation of voter's preferences. For candidates i and j, the voters
	 * choice is recorded in position CondorcetBallotDesign.position(i,j,this.k).
	 */
	public final byte[] matrix;

	public CondorcetBallot(int numCandidates) {
		super();
		this.k = numCandidates;
		int size = CondorcetBallotDesign.matrixSize(this.k);
		matrix = new byte[size < 0 ? 0 : size];
	}

	public void record(int i, int j, byte voteChoice) {
		if (!(0 <= i && i < j && j < k) || (matrix == null))
			return;
		try {
			matrix[CondorcetBallotDesign.position(i, j, k)] = voteChoice;
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(CondorcetBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<k>");
		sb.print(k);
		sb.print("</k>");
		sb.print("<matrix>");
		try {
			for (int i = 0; i < k; i++) {
				for (int j = i + 1; j < k; j++) {
					sb.print("<entry>");
					sb.print("<i>");
					sb.print(i);
					sb.print("</i>");
					sb.print("<j>");
					sb.print(j);
					sb.print("</j>");
					sb.print("<choice>");
					sb.print(CondorcetBallotDesign
							.choiceToString(matrix[CondorcetBallotDesign.position(i, j, k)]));
					sb.print("</choice>");
					sb.print("</entry>");
				}
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</matrix>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static Ballot fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		int k = Util.readSimpleIntTag(r, "k");
		CondorcetBallot b = new CondorcetBallot(k);
		Util.swallowTag(r, "matrix");
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				Util.swallowTag(r, "entry");
				if (i != Util.readSimpleIntTag(r, "i")
						|| j != Util.readSimpleIntTag(r, "j")) {
					throw new IOException("Expected pair (" + i + "," + j + ")");
				}
				byte choice = CondorcetBallotDesign
						.stringToChoice(Util.readSimpleTag(r, "choice"));
				b.record(i, j, choice);
				Util.swallowEndTag(r, "entry");
			}
		}

		Util.swallowEndTag(r, "matrix");
		return b;
	}
}