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
import civitas.crypto.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;

public class ElGamalPrivateKeyC extends ElGamalAbstractKey
		implements ElGamalPrivateKey {

	public final CivitasBigInteger x;

	public ElGamalPrivateKeyC(CivitasBigInteger x, ElGamalParameters params) {
		super(params);
		this.x = x;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalPrivateKey>");

		s.print("<params>");
		if (this.params != null) {
			this.params.toXML(s);
		}
		s.print("</params>");
		s.print("<x>");
		if (this.x != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(x), s);
		s.print("</x>");

		s.print("</elGamalPrivateKey>");
	}

	public static ElGamalPrivateKeyC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalPrivateKey");
		Util.swallowTag(r, "params");
		ElGamalParameters params = ElGamalParametersC.fromXML(r);
		Util.swallowEndTag(r, "params");
		String x = Util.unescapeString(Util.readSimpleTag(r, "x"));
		Util.swallowEndTag(r, "elGamalPrivateKey");
		return new ElGamalPrivateKeyC(CryptoFactoryC.stringToBigInt(x), params);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;

		ElGamalPrivateKeyC k = (ElGamalPrivateKeyC) o;
		return this.x.equals(k.x);
	}

}
