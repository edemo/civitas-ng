/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keypairshare;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoUtil;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;

public class ElGamalKeyPairShare {
	public final ElGamalParameters params;
	public final ElGamalPublicKey pubKey;
	public final ElGamalPrivateKey privKey;

	public ElGamalKeyPairShare(ElGamalParameters params, ElGamalPublicKey pubKey,
			ElGamalPrivateKey privKey) {
		this.params = params;
		this.pubKey = pubKey;
		this.privKey = privKey;
	}

	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<elGamalKeyPairShare>");

		if (this.pubKey != null) {
			this.pubKey.toXML(sb);
		}
		if (this.privKey != null) {
			this.privKey.toXML(sb);
		}

		sb.print("</elGamalKeyPairShare>");
	}

	public static ElGamalKeyPairShare fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		try {
			Util.swallowTag(r, "elGamalKeyPairShare");
			ElGamalPublicKey pubKey = CryptoUtil.factory().elGamalPublicKeyFromXML(r);
			ElGamalPrivateKey privKey = CryptoUtil.factory()
					.elGamalPrivateKeyFromXML(r);
			Util.swallowEndTag(r, "elGamalKeyPairShare");

			ElGamalParameters params = pubKey == null ? null : pubKey.getParams();
			return new ElGamalKeyPairShare(params, pubKey, privKey);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
	}

}
