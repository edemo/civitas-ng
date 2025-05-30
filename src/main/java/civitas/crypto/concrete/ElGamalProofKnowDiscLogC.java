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
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalProofKnowDiscLog;
import civitas.crypto.algorithms.VerifyElGamalProofKnowDiscLog;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

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

	@Use
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;

	@Override
	public boolean verify(ElGamalParameters prms) {
		return verifyElGamalProofKnowDiscLog.apply(this, prms);
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
			Util.escapeString(Util.fromBigInt(this.a), s);
		s.print("</a>");
		s.print("<c>");
		if (this.c != null)
			Util.escapeString(Util.fromBigInt(this.c), s);
		s.print("</c>");
		s.print("<r>");
		if (this.r != null)
			Util.escapeString(Util.fromBigInt(this.r), s);
		s.print("</r>");
		s.print("<v>");
		if (this.v != null)
			Util.escapeString(Util.fromBigInt(this.v), s);
		s.print("</v>");

		s.print("</elGamalProofKnowDiscLog>");
	}

}