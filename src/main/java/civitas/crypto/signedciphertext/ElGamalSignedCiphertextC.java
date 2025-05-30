/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.signedciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.util.CivitasBigInteger;

public class ElGamalSignedCiphertextC extends ElGamalCiphertext
		implements ElGamalSignedCiphertext {
	public final CivitasBigInteger c;
	public final CivitasBigInteger d;

	public ElGamalSignedCiphertextC(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, CivitasBigInteger d) {
		super(a, b);
		this.c = c;
		this.d = d;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	public void toXML(PrintWriter s) {
		s.print("<elGamalSignedCiphertext>");
		s.print("<a>");
		if (a != null)
			Util.escapeString(Util.fromBigInt(this.a), s);
		s.print("</a>");
		s.print("<b>");
		if (b != null)
			Util.escapeString(Util.fromBigInt(this.b), s);
		s.print("</b>");
		s.print("<c>");
		if (c != null)
			Util.escapeString(Util.fromBigInt(this.c), s);
		s.print("</c>");
		s.print("<d>");
		if (d != null)
			Util.escapeString(Util.fromBigInt(this.d), s);
		s.print("</d>");
		s.print("</elGamalSignedCiphertext>");
	}

}
