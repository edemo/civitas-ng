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
import java.io.StringWriter;

import civitas.crypto.CryptoFactory;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.MessageDigest;

/**
 * A teller's share of a public key.
 */
public class TabTellerKeyShare implements XMLSerializable {
	public final static String META = "ttKeyShare";

	public final int tellerIndex;
	public final ElGamalKeyShare keyShare;

	public TabTellerKeyShare(int tellerIndex, ElGamalKeyShare keyShare) {
		this.tellerIndex = tellerIndex;
		this.keyShare = keyShare;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<tabTellerKeyShare>");

		sb.print("<index>");
		sb.print(this.tellerIndex);
		sb.print("</index>");
		if (this.keyShare != null) {
			this.keyShare.toXML(sb);
		}

		sb.print("</tabTellerKeyShare>");
	}

	public TabTellerKeyShareCommitment constructCommitment() {
		CryptoFactory factory = CryptoUtil.factory();
		String hash = null;
		if (factory != null) {
			MessageDigest md = factory.messageDigest();
			if (md != null) {
				StringWriter sb = new StringWriter();
				this.toXML(new PrintWriter(sb));
				md.update(sb.toString());
				hash = factory.constBytesToBase64(md.digest());
			}
		}
		return new TabTellerKeyShareCommitment(tellerIndex, hash);
	}

	public static TabTellerKeyShare fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "tabTellerKeyShare");
		int index = Util.readSimpleIntTag(r, "index");
		ElGamalKeyShare keyShare = null;
		try {
			keyShare = CryptoUtil.factory().elGamalKeyShareFromXML(r);
		} catch (NullPointerException imposs) {
		}
		Util.swallowEndTag(r, "tabTellerKeyShare");
		return new TabTellerKeyShare(index, keyShare);
	}
}