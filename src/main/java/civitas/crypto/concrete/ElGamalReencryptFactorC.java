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
import civitas.crypto.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

public class ElGamalReencryptFactorC implements ElGamalReencryptFactor {
	protected final CivitasBigInteger r;

	public ElGamalReencryptFactorC(CivitasBigInteger r) {
		this.r = r;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<r>");
		if (this.r != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.r), s);
		s.print("</r>");
	}

	public static ElGamalReencryptFactor fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, "r"));
		return new ElGamalReencryptFactorC(CryptoFactoryC.stringToBigInt(s));
	}

}
