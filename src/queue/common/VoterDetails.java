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
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.PublicKey;

/**
 * Details of an individual voter. Details include the voter's name (a string
 * uniquely identifying the voter), an ElGamal public key, an RSA public key,
 * and the block the voter belongs in.
 */
public class VoterDetails implements XMLSerializable {
	public static final String OPENING_TAG = "voterDetails";

	/**
	 * The name of the voter, which must be unique.
	 */
	public final String name;

	/**
	 * The voter's El Gamal public key
	 */
	public final ElGamalPublicKey egPublicKey;

	/**
	 * The voter's other public key
	 */
	public final PublicKey publicKey;

	/**
	 * The voter's block.
	 *
	 */
	public final int voterBlock;

	public VoterDetails(String name, ElGamalPublicKey egPublicKey, PublicKey publicKey, int voterBlock) {
		this.name = name;
		this.egPublicKey = egPublicKey;
		this.publicKey = publicKey;
		this.voterBlock = voterBlock;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");

		sb.print("<name>");
		Util.escapeString(this.name, sb);
		sb.print("</name>");
		if (egPublicKey != null)
			egPublicKey.toXML(sb);
		if (publicKey != null)
			publicKey.toXML(sb);
		sb.print("<block>");
		sb.print(this.voterBlock);
		sb.print("</block>");
		sb.print("</" + OPENING_TAG + ">");
	}

	public static VoterDetails fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);
		String name = Util.unescapeString(Util.readSimpleTag(r, "name"));
		ElGamalPublicKey egPublicKey = null;
		if (Util.isNextTag(r, ElGamalPublicKey.EG_OPENING_TAG)) {
			try {
				egPublicKey = CryptoUtil.factory().elGamalPublicKeyFromXML(r);
			} catch (NullPointerException imposs) {
			}
		}
		PublicKey publicKey = null;
		if (Util.isNextTag(r, PublicKey.OPENING_TAG)) {
			try {
				publicKey = CryptoUtil.factory().publicKeyFromXML(r);
			} catch (NullPointerException imposs) {
			}
		}

		int voterBlock = Util.readSimpleIntTag(r, "block");
		Util.swallowEndTag(r, OPENING_TAG);
		return new VoterDetails(name, egPublicKey, publicKey, voterBlock);
	}
}