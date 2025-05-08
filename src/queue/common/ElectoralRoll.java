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
 * An electoral roll: a collection of voter details. This data structure
 * identifies which voters may participate in an election. The VoterDetails
 * structure provides the unique name of a voter, the voter's keys, and the
 * block the voter belongs to. The electoral roll is posted to the bulletin
 * board by the registrar.
 */
public class ElectoralRoll implements XMLSerializable {
	public final static String META = "electoralRoll";

	// an array of voter details
	public final VoterDetails[] roll;

	public ElectoralRoll(VoterDetails[] roll) {
		this.roll = roll;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<electoralRoll>");

		if (this.roll != null) {
			sb.print("<size>");
			sb.print(this.roll.length);
			sb.print("</size>");
			for (int i = 0; i < roll.length; i++) {
				try {
					VoterDetails c = roll[i];
					if (c != null) {
						c.toXML(sb);
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");
		}

		sb.print("</electoralRoll>");
	}

	public static ElectoralRoll fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "electoralRoll");
		int size = Util.readSimpleIntTag(r, "size");
		VoterDetails[] roll = new VoterDetails[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			VoterDetails c = VoterDetails.fromXML(r);
			try {
				roll[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "electoralRoll");
		return new ElectoralRoll(roll);
	}
}