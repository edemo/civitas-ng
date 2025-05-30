/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickey;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;
import civitas.crypto.msg.PublicKeyMsgC;
import civitas.crypto.privatekey.PrivateKeyC;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.signature.Signature;

public class PublicKeyC implements PublicKey {
	private static final int AUTHENTICATION_NONCE_LENGTH = 64;
	public final java.security.PublicKey k;
	final String name;

	public PublicKeyC(java.security.PublicKey k, String name) {
		this.k = k;
		this.name = name;
	}

	public String name() {
		return name;
	}

	public boolean isAuthorized(Object authPrf, boolean executeNow) {
		if (authPrf instanceof PrivateKeyC) {
			PrivateKeyC privKey = (PrivateKeyC) authPrf;
			// check if privKey is the matching private key for this public key

			PublicKeyMsg m = new PublicKeyMsgC(CryptoFactoryC.singleton()
					.freshNonceBase64(AUTHENTICATION_NONCE_LENGTH));
			Signature sig = CryptoFactoryC.singleton().signature(privKey, m);
			return CryptoFactoryC.singleton().publicKeyVerifySignature(this, sig, m);

		}
		return false;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(OPENING_TAG);
		s.print('>');
		s.print("<name>");
		s.print(name);
		s.print("</name>");
		s.print("<key>");
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		byte[] bs = factory.publicKeyToBytes(k);
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</key>");
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

}
