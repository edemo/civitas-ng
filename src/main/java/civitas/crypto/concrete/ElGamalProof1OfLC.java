/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.CiphertextList;
import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalProof1OfL;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.algorithms.VerifyElGamalProof1OfLC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalProof1OfLC implements ElGamalProof1OfL {
	@Use
	VerifyElGamalProof1OfLC verifyElGamalProof1OfLC;

	public final int L;
	public final CivitasBigInteger[] dvs;
	public final CivitasBigInteger[] rvs;

	public ElGamalProof1OfLC(int L, CivitasBigInteger[] dvs,
			CivitasBigInteger[] rvs) {
		this.L = L;
		this.dvs = dvs;
		this.rvs = rvs;
		if (dvs == null || rvs == null || dvs.length != L || rvs.length != L) {
			throw new IllegalArgumentException("Bad args");
		}
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalProof1OfL>");
		s.print("<size>");
		s.print(L);
		s.print("</size>");
		for (int i = 0; i < L; i++) {
			s.print("<dv>");
			if (dvs[i] != null)
				Util.escapeString(Util.fromBigInt(this.dvs[i]), s);
			s.print("</dv>");
		}
		for (int i = 0; i < L; i++) {
			s.print("<rv>");
			if (rvs[i] != null)
				Util.escapeString(Util.fromBigInt(this.rvs[i]), s);
			s.print("</rv>");
		}
		s.print("</elGamalProof1OfL>");
	}

	@Override
	public boolean equals(ElGamalProof1OfL p) {
		if (p instanceof ElGamalProof1OfLC) {
			ElGamalProof1OfLC that = (ElGamalProof1OfLC) p;
			if (this.L != that.L)
				return false;

			for (int i = 0; i < L; i++) {
				try {
					if (!dvs[i].equals(that.dvs[i]))
						return false;
					if (!rvs[i].equals(that.rvs[i]))
						return false;
				} catch (NullPointerException e) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	@Deprecated
	public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts,
			int L, ElGamalCiphertext msg) {
		throw new UnsupportedOperationException("use VerifyElGamalProof1OfLC");
	}

}
