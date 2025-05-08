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
 * The encrypted capabilities of all voters. This data structure is produced by
 * combining the encrypted capability shares posted by each registration teller,
 * and is used as input to the electoral roll capability renecryption mix.
 */
public class ElectoralRollCapabilities implements XMLSerializable {
	public static final String OPENING_TAG = "electoralRollCapabilities";

	// an array of voter capabilities
	public final VoterEncCapabilities[] roll;

	public ElectoralRollCapabilities(VoterEncCapabilities[] roll)

	{
		this.roll = roll;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		if (this.roll != null) {
			sb.print("<size>");
			sb.print(this.roll.length);
			sb.print("</size>");
			for (int i = 0; i < roll.length; i++) {
				try {
					VoterEncCapabilities c = roll[i];
					if (c != null) {
						c.toXML(sb);
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");
		}

		sb.print("</" + OPENING_TAG + ">");
	}

	public static ElectoralRollCapabilities fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);
		int size = Util.readSimpleIntTag(r, "size");
		VoterEncCapabilities[] roll = new VoterEncCapabilities[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			VoterEncCapabilities c = VoterEncCapabilities.fromXML(r);
			try {
				roll[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, OPENING_TAG);
		return new ElectoralRollCapabilities(roll);
	}
}