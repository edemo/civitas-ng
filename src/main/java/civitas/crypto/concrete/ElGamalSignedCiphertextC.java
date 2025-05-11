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
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

public class ElGamalSignedCiphertextC extends ElGamalCiphertextC
		implements ElGamalSignedCiphertext {
	public final CivitasBigInteger c;
	public final CivitasBigInteger d;

	public ElGamalSignedCiphertextC(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, CivitasBigInteger d) {
		super(a, b);
		this.c = c;
		this.d = d;
	}

	@Override
	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalSignedCiphertext>");
		s.print("<a>");
		if (a != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.a), s);
		s.print("</a>");
		s.print("<b>");
		if (b != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.b), s);
		s.print("</b>");
		s.print("<c>");
		if (c != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.c), s);
		s.print("</c>");
		s.print("<d>");
		if (d != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.d), s);
		s.print("</d>");
		s.print("</elGamalSignedCiphertext>");
	}

	public static ElGamalSignedCiphertext fromXMLsub(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalSignedCiphertext");
		CivitasBigInteger a = null;
		String sa = Util.readSimpleTag(r, "a");
		if (sa != null && sa.length() > 0) {
			a = CryptoFactoryC.stringToBigInt(Util.unescapeString(sa));
		}

		CivitasBigInteger b = null;
		String sb = Util.readSimpleTag(r, "b");
		if (sb != null && sb.length() > 0) {
			b = CryptoFactoryC.stringToBigInt(Util.unescapeString(sb));
		}

		CivitasBigInteger c = null;
		String sc = Util.readSimpleTag(r, "c");
		if (sc != null && sc.length() > 0) {
			c = CryptoFactoryC.stringToBigInt(Util.unescapeString(sc));
		}

		CivitasBigInteger d = null;
		String sd = Util.readSimpleTag(r, "d");
		if (sd != null && sd.length() > 0) {
			d = CryptoFactoryC.stringToBigInt(Util.unescapeString(sd));
		}

		Util.swallowEndTag(r, "elGamalSignedCiphertext");
		return new ElGamalSignedCiphertextC(a, b, c, d);
	}

	@Override
	public void toUnsignedCiphertextXML(PrintWriter sb) {
		super.toXML(sb);
	}

}
