/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keypairshare;

import java.io.PrintWriter;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.privatekey.ElGamalPrivateKeyToXML;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyToXML;
import civitas.util.Use;

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

	@Use
	ElGamalPublicKeyToXML elGamalPublicKeyToXML;
	@Use
	ElGamalPrivateKeyToXML elGamalPrivateKeyToXML;

	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<elGamalKeyPairShare>");

		if (this.pubKey != null) {
			elGamalPublicKeyToXML.apply((ElGamalPublicKey) this.pubKey, sb);
		}
		if (this.privKey != null) {
			elGamalPrivateKeyToXML.apply((ElGamalPrivateKey) this.privKey, sb);
		}

		sb.print("</elGamalKeyPairShare>");
	}

}
