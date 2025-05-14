/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ElGamalAbstractKey;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

public class ElGamalPublicKeyC extends ElGamalAbstractKey
		implements ElGamalPublicKey {
	public final CivitasBigInteger y;

	public ElGamalPublicKeyC(CivitasBigInteger y, ElGamalParameters params) {
		super(params);
		this.y = y;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(EG_OPENING_TAG);
		s.print('>');

		s.print("<params>");
		if (this.params != null) {
			this.params.toXML(s);
		}
		s.print("</params>");
		s.print("<y>");
		if (this.y != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.y), s);
		s.print("</y>");

		s.print("</");
		s.print(EG_OPENING_TAG);
		s.print('>');
	}

	public static ElGamalPublicKeyC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, EG_OPENING_TAG);
		Util.swallowTag(r, "params");
		ElGamalParameters params = ElGamalParametersC.fromXML(r);
		Util.swallowEndTag(r, "params");
		String y = Util.unescapeString(Util.readSimpleTag(r, "y"));
		Util.swallowEndTag(r, EG_OPENING_TAG);
		return new ElGamalPublicKeyC(CryptoFactoryC.stringToBigInt(y), params);
	}

	public boolean isAuthorized(Object prf) {
		// check if prf is the matching ElGamalPrivateKey
		if (prf instanceof ElGamalPrivateKeyC) {
			ElGamalPrivateKeyC k = (ElGamalPrivateKeyC) prf;
			ElGamalParametersC param = (ElGamalParametersC) this.params;
			return y.equals(param.g.modPow(k.x, param.p));
		}
		return false;
	}

	public String name() {
		return "ElGamalPublicKey-" + CryptoFactoryC.bigIntToString(y);
	}

}
