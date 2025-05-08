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
import civitas.crypto.ElGamalSignedCiphertext;

/**
 * Associates a collection of encrypted capability shares and a voter block with
 * a voter name. Each registration teller posts one of these for each voter.
 */
public class VoterEncCapabilityShares implements XMLSerializable {
	public static final String OPENING_TAG = "voterEncCapabilityShares";
	/**
	 * Which registration teller did these capabilities come from.
	 */
	public final int regTellerIndex;

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
	public final ElGamalSignedCiphertext[] encCapabilityShares;

	public VoterEncCapabilityShares(int regTellerIndex, String name, int voterBlock,
			ElGamalSignedCiphertext[] encCapabilityShares) {
		this.regTellerIndex = regTellerIndex;
		this.name = name;
		this.voterBlock = voterBlock;
		this.encCapabilityShares = encCapabilityShares;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");

		sb.print("<regTellerIndex>");
		sb.print(this.regTellerIndex);
		sb.print("</regTellerIndex>");
		sb.print("<name>");
		Util.escapeString(this.name, sb);
		sb.print("</name>");
		sb.print("<voterBlock>");
		sb.print(this.voterBlock);
		sb.print("</voterBlock>");
		sb.print("<encCapabilityShares>");
		if (encCapabilityShares != null) {
			sb.print("<size>");
			sb.print(this.encCapabilityShares.length);
			sb.print("</size>");
			for (int i = 0; i < encCapabilityShares.length; i++) {
				try {
					ElGamalSignedCiphertext vc = encCapabilityShares[i];
					if (vc != null)
						vc.toXML(sb);
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");

		}
		sb.print("</encCapabilityShares>");

		sb.print("</" + OPENING_TAG + ">");
	}

	public static VoterEncCapabilityShares fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);
		int regTellerIndex = Util.readSimpleIntTag(r, "regTellerIndex");
		String name = Util.unescapeString(Util.readSimpleTag(r, "name"));

		int voterBlock = Util.readSimpleIntTag(r, "voterBlock");

		Util.swallowTag(r, "encCapabilityShares");
		int size = Util.readSimpleIntTag(r, "size");
		ElGamalSignedCiphertext[] caps = new ElGamalSignedCiphertext[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			ElGamalSignedCiphertext c = null;
			try {
				c = CryptoUtil.factory().elGamalSignedCiphertextFromXML(r);
			} catch (NullPointerException imposs) {
			}
			try {
				caps[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "encCapabilityShares");

		Util.swallowEndTag(r, OPENING_TAG);
		return new VoterEncCapabilityShares(regTellerIndex, name, voterBlock, caps);
	}
}