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
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalProofKnowDiscLog;
import civitas.util.CivitasBigInteger;

/**
 * Proof that an entity knows x in v = g^x.
 */
public class ElGamalProofKnowDiscLogC implements ElGamalProofKnowDiscLog {

	/*
	 * p,q,g are the parameters of the ElGamal (not included here). z = random in
	 * Z_q a = g^z mod p c = hash(v,a) r = (z + cx) mod q
	 * 
	 * To verify proof, check that g^r = av^c (mod p)
	 */
	public final CivitasBigInteger a;
	public final CivitasBigInteger c;
	public final CivitasBigInteger r;
	public final CivitasBigInteger v;

	public ElGamalProofKnowDiscLogC(CivitasBigInteger a, CivitasBigInteger c,
			CivitasBigInteger r, CivitasBigInteger v) {
		this.a = a;
		this.c = c;
		this.r = r;
		this.v = v;
	}

	@Override
	public boolean verify(ElGamalParameters prms) {
		if (!(prms instanceof ElGamalParametersC))
			return false;
		ElGamalParametersC params = (ElGamalParametersC) prms;
		try {
			CivitasBigInteger u = params.g.modPow(r, params.p);
			CivitasBigInteger w = a.modMultiply(v.modPow(c, params.p), params.p);

			return u.equals(w);
		} catch (NullPointerException e) {
			return false;
		} catch (ArithmeticException e) {
			return false;
		}
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalProofKnowDiscLog>");

		s.print("<a>");
		if (this.a != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.a), s);
		s.print("</a>");
		s.print("<c>");
		if (this.c != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.c), s);
		s.print("</c>");
		s.print("<r>");
		if (this.r != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.r), s);
		s.print("</r>");
		s.print("<v>");
		if (this.v != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.v), s);
		s.print("</v>");

		s.print("</elGamalProofKnowDiscLog>");
	}

	public static ElGamalProofKnowDiscLogC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProofKnowDiscLog");
		String a = Util.unescapeString(Util.readSimpleTag(r, "a"));
		String c = Util.unescapeString(Util.readSimpleTag(r, "c"));
		String rr = Util.unescapeString(Util.readSimpleTag(r, "r"));
		String v = Util.unescapeString(Util.readSimpleTag(r, "v"));

		Util.swallowEndTag(r, "elGamalProofKnowDiscLog");
		return new ElGamalProofKnowDiscLogC(CryptoFactoryC.stringToBigInt(a),
				CryptoFactoryC.stringToBigInt(c), CryptoFactoryC.stringToBigInt(rr),
				CryptoFactoryC.stringToBigInt(v));
	}
}