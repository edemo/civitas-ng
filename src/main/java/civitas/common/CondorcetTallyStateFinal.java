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
 * TallyStateFinal for a CondorcetBallotDesign.
 */
public class CondorcetTallyStateFinal extends TallyStateFinal {
	private final int[][] matrix;
	private final int size;

	CondorcetTallyStateFinal(int[][] matrix, int size) {
		super();
		int[][] as = null;
		if (matrix != null)
			as = matrix.clone();
		this.matrix = as;
		this.size = size;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(CondorcetBallotDesign.KIND);
		sb.print("</kind>");
		sb.print("<count>");
		sb.print(size);
		sb.print("</count>");

		sb.print("<matrix>");
		try {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					if (i == j)
						continue;
					sb.print("<entry>");
					sb.print("<i>");
					sb.print(i);
					sb.print("</i>");
					sb.print("<j>");
					sb.print(j);
					sb.print("</j>");
					sb.print("<tally>");
					sb.print(matrix[i][j]);
					sb.print("</tally>");
					sb.print("</entry>");
				}
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</matrix>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static TallyStateFinal fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		int count = Util.readSimpleIntTag(r, "count");

		int[][] matrix = new int[count < 0 ? 0 : count][count < 0 ? 0 : count];

		Util.swallowTag(r, "matrix");
		try {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					if (i == j)
						continue;
					try {
						Util.swallowTag(r, "entry");
						if (i != Util.readSimpleIntTag(r, "i")
								|| j != Util.readSimpleIntTag(r, "j")) {
							throw new IOException("Expected pair (" + i + "," + j + ")");
						}
						matrix[i][j] = Util.readSimpleIntTag(r, "tally");
					} catch (NullPointerException imposs) {
					}
				}
			}
		} catch (NullPointerException imposs) {
		}

		Util.swallowEndTag(r, "matrix");
		CondorcetTallyStateFinal b = new CondorcetTallyStateFinal(matrix, count);
		return b;
	}
}