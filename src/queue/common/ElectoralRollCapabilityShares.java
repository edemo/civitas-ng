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
 * Encrypted capability shares for all voters. This data structure is produced
 * by each registration teller at the start of registration. Each registration
 * teller produces capability shares for each voter, and posts encryptions of
 * these shares to the bulletin board.
 */
public class ElectoralRollCapabilityShares implements XMLSerializable {
	private final static String META = "electoralRollCapShares";
	public static final String OPENING_TAG = "electoralRollCapShares";

	public static String meta(int tellerIndex, int voterBlock) {
		return META + ":teller:" + tellerIndex + ":voterBlock:" + voterBlock;
	}

	// an array of voter capabilities
	public final VoterEncCapabilityShares[] roll;

	/**
	 * Which registration teller created these capabilities
	 */
	public final int tellerIndex;

	/**
	 * Which voter block do these voters belong to
	 */
	public final int voterBlock;

	public ElectoralRollCapabilityShares(VoterEncCapabilityShares[] roll,

			int tellerIndex, int voterBlock) {
		this.roll = roll;
		this.tellerIndex = tellerIndex;
		this.voterBlock = voterBlock;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<teller>");
		sb.print(this.tellerIndex);
		sb.print("</teller>");
		sb.print("<voterBlock>");
		sb.print(this.voterBlock);
		sb.print("</voterBlock>");

		if (this.roll != null) {
			sb.print("<size>");
			sb.print(this.roll.length);
			sb.print("</size>");
			for (int i = 0; i < roll.length; i++) {
				try {
					VoterEncCapabilityShares c = roll[i];
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

	public static ElectoralRollCapabilityShares fromXML(Reader r) throws IllegalArgumentException, IOException

	{
		Util.swallowTag(r, OPENING_TAG);

		int tellerIndex = Util.readSimpleIntTag(r, "teller");
		int voterBlock = Util.readSimpleIntTag(r, "voterBlock");
		int size = Util.readSimpleIntTag(r, "size");
		VoterEncCapabilityShares[] roll = new VoterEncCapabilityShares[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			VoterEncCapabilityShares c = VoterEncCapabilityShares.fromXML(r);
			try {
				roll[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, OPENING_TAG);
		return new ElectoralRollCapabilityShares(roll, tellerIndex, voterBlock);
	}
}