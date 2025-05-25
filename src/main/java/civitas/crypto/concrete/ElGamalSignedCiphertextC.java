/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.PrintWriter;
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

	@Override
	@Deprecated
	public void toUnsignedCiphertextXML(PrintWriter sb) {
		super.toXML(sb);
	}

}
