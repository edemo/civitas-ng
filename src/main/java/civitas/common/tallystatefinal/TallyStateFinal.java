/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tallystatefinal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import civitas.common.CommonConstants;
import civitas.common.Util;
import civitas.common.ballotdesign.BallotDesign;
import lombok.Data;

/**
 * TallyStateFinal for a CondorcetBallotDesign.
 */
@Data
public class TallyStateFinal implements CommonConstants {
	private final Integer size;
	private final Integer[][] matrix;

	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + TallyStateFinalOPENING_TAG + ">");
		sb.print("<kind>");
		sb.print(BallotDesign.KIND);
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
		sb.print("</" + TallyStateFinalOPENING_TAG + ">");
	}

	public static TallyStateFinal fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Integer count = Util.readSimpleIntTag(r, "count");

		Integer[][] matrix = new Integer[count < 0 ? 0 : count][count < 0 ? 0
				: count];

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
		TallyStateFinal b = new TallyStateFinal(count, matrix);
		return b;
	}
}