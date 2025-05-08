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

import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalCiphertext;

/**
 * Associates a collection of encrypted capabilities and a voter block with a
 * voter name. That is, for a given voter, this data structure is a list of
 * encrypted capabilities, and the block the voter belongs in. This data
 * structure created by combining a collection of VoterEncCapabilityShares
 * objects.
 */
public class VoterEncCapabilities implements XMLSerializable {
	public static final String OPENING_TAG = "voterEncCapabilities";

	/**
	 * The voter's name
	 */
	public final String name;

	/**
	 * for the purposes of blocking, voters are divided into blocks, according to
	 * the security parameters of the election details.
	 */
	public final int voterBlock;

	// the order of these capabilities is important: the ith capability must
	// be used for the ith context, i.e., the ith produced vote.
	public final ElGamalCiphertext[] encCapabilities;

	public VoterEncCapabilities(String name, int voterBlock, ElGamalCiphertext[] encCapabilities)

	{
		this.name = name;
		this.voterBlock = voterBlock;
		this.encCapabilities = encCapabilities;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");
		sb.print("<name>");
		Util.escapeString(this.name, sb);
		sb.print("</name>");
		sb.print("<voterBlock>");
		sb.print(this.voterBlock);
		sb.print("</voterBlock>");
		sb.print("<encCapabilities>");
		if (encCapabilities != null) {
			sb.print("<size>");
			sb.print(this.encCapabilities.length);
			sb.print("</size>");
			for (int i = 0; i < encCapabilities.length; i++) {
				try {
					ElGamalCiphertext vc = encCapabilities[i];
					if (vc != null)
						vc.toXML(sb);
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");

		}
		sb.print("</encCapabilities>");

		sb.print("</" + OPENING_TAG + ">");
	}

	public static VoterEncCapabilities fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);
		String name = Util.unescapeString(Util.readSimpleTag(r, "name"));

		int voterBlock = Util.readSimpleIntTag(r, "voterBlock");

		Util.swallowTag(r, "encCapabilities");
		int size = Util.readSimpleIntTag(r, "size");
		ElGamalCiphertext[] caps = new ElGamalCiphertext[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			ElGamalCiphertext c = null;
			try {
				c = CryptoUtil.factory().elGamalCiphertextFromXML(r);
			} catch (NullPointerException imposs) {
			}
			try {
				caps[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "encCapabilities");

		Util.swallowEndTag(r, OPENING_TAG);
		return new VoterEncCapabilities(name, voterBlock, caps);
	}
}