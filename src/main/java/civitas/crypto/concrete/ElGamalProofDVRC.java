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
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

public class ElGamalProofDVRC implements ElGamalProofDVR {

	public final ElGamalCiphertextC e;
	public final ElGamalCiphertextC eprime;
	public final CivitasBigInteger c;
	public final CivitasBigInteger w;
	public final CivitasBigInteger r;
	public final CivitasBigInteger u;

	public ElGamalProofDVRC(ElGamalCiphertextC e, ElGamalCiphertextC eprime,
			CivitasBigInteger c, CivitasBigInteger w, CivitasBigInteger r,
			CivitasBigInteger u) {
		this.e = e;
		this.eprime = eprime;
		this.c = c;
		this.w = w;
		this.r = r;
		this.u = u;
	}

	@Override
	public ElGamalCiphertext getE() {
		return e;
	}

	@Override
	public ElGamalCiphertext getEprime() {
		return eprime;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalProofDVR>");
		e.toXML(s);
		eprime.toXML(s);
		s.print("<c>");
		Util.escapeString(Util.fromBigInt(c), s);
		s.print("</c>");
		s.print("<w>");
		Util.escapeString(Util.fromBigInt(w), s);
		s.print("</w>");
		s.print("<r>");
		Util.escapeString(Util.fromBigInt(r), s);
		s.print("</r>");
		s.print("<u>");
		Util.escapeString(Util.fromBigInt(u), s);
		s.print("</u>");
		s.print("</elGamalProofDVR>");
	}

	@Override
	public boolean verify(ElGamalPublicKey K, ElGamalPublicKey verifierKey) {
		throw new UnsupportedOperationException("use VerifyElGamalProofDVR");
	}

}
