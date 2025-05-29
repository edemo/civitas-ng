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
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;

/**
 * To prove that log v = log w, where v = g_1^x and w = g_2^x, let: z = random
 * in Z_q a = g_1^z b = g_2^z c = hash(v,w,a,b) r = (z + cx) mod q The proof is
 * (a,b,c,r). To verify, check that g_1^r = av^c (mod p) and g_2^r = bw^c (mod
 * p).
 */
public class ElGamalProofDiscLogEqualityC
		implements ElGamalProofDiscLogEquality {

	public final CivitasBigInteger g1;
	public final CivitasBigInteger g2;

	public final CivitasBigInteger v;
	public final CivitasBigInteger w;

	public final CivitasBigInteger a;
	public final CivitasBigInteger b;
	public final CivitasBigInteger c;
	public final CivitasBigInteger r;

	public ElGamalProofDiscLogEqualityC(CivitasBigInteger g1,
			CivitasBigInteger g2, CivitasBigInteger a, CivitasBigInteger v,
			CivitasBigInteger w, CivitasBigInteger b, CivitasBigInteger c,
			CivitasBigInteger r) {
		this.g1 = g1;
		this.g2 = g2;
		this.v = v;
		this.w = w;
		this.a = a;
		this.b = b;
		this.c = c;
		this.r = r;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<egPrfKnwDscLog>");

		s.print("<g1>");
		if (this.g1 != null)
			Util.escapeString(Util.fromBigInt(this.g1), s);
		s.print("</g1>");
		s.print("<g2>");
		if (this.g2 != null)
			Util.escapeString(Util.fromBigInt(this.g2), s);
		s.print("</g2>");
		s.print("<v>");
		if (this.v != null)
			Util.escapeString(Util.fromBigInt(this.v), s);
		s.print("</v>");
		s.print("<w>");
		if (this.w != null)
			Util.escapeString(Util.fromBigInt(this.w), s);
		s.print("</w>");
		s.print("<a>");
		if (this.a != null)
			Util.escapeString(Util.fromBigInt(this.a), s);
		s.print("</a>");
		s.print("<b>");
		if (this.b != null)
			Util.escapeString(Util.fromBigInt(this.b), s);
		s.print("</b>");
		s.print("<c>");
		if (this.c != null)
			Util.escapeString(Util.fromBigInt(this.c), s);
		s.print("</c>");
		s.print("<r>");
		if (this.r != null)
			Util.escapeString(Util.fromBigInt(this.r), s);
		s.print("</r>");

		s.print("</egPrfKnwDscLog>");
	}

}