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
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.PETCommitment;
import civitas.crypto.PETDecommitment;
import civitas.crypto.PETShare;
import civitas.crypto.algorithms.ConstructElGamalDiscLogEqualityProof;
import civitas.crypto.algorithms.ConstructPETDecommitment;
import civitas.crypto.algorithms.CryptoHash;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;
import civitas.util.Use;

public class PETShareC implements PETShare {
	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;
	@Use
	ConstructPETDecommitment constructPETDecommitment;

	@Use
	CryptoHash hash;

	public final ElGamalCiphertextC ciphertext1;
	public final ElGamalCiphertextC ciphertext2;

	public final CivitasBigInteger exponent;

	public PETShareC(ElGamalCiphertextC ciphertext1,
			ElGamalCiphertextC ciphertext2, CivitasBigInteger exponent) {
		this.ciphertext1 = ciphertext1;
		this.ciphertext2 = ciphertext2;
		this.exponent = exponent;
		DI.fill(this);
	}

	@Override
	public ElGamalCiphertext ciphertext1() {
		return ciphertext1;
	}

	@Override
	public ElGamalCiphertext ciphertext2() {
		return ciphertext2;
	}

	@Deprecated
	public ElGamalCiphertext ciphertextA() {
		return ciphertext1;
	}

	@Deprecated
	public ElGamalCiphertext ciphertextB() {
		return ciphertext2;
	}

	public CivitasBigInteger exponent() {
		return exponent;
	}

	// return a hash of the ciphertexts and exponent
	@Override
	public PETCommitment commitment(ElGamalParameters params) {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) params;

			CivitasBigInteger zi = exponent;
			CivitasBigInteger d = ciphertext1.a.modDivide(ciphertext2.a, ps.p);
			CivitasBigInteger e = ciphertext1.b.modDivide(ciphertext2.b, ps.p);

			CivitasBigInteger di = d.modPow(zi, ps.p);
			CivitasBigInteger ei = e.modPow(zi, ps.p);

			return new PETCommitmentC(hash.apply(di, ei));
		} catch (ClassCastException e) {
			return null;
		}
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.append("<petShare>");
		if (this.ciphertext1 != null) {
			this.ciphertext1.toXML(sb);
		}
		if (this.ciphertext2 != null) {
			this.ciphertext2.toXML(sb);
		}
		if (this.exponent != null) {
			sb.append("<exponent>");
			Util.escapeString(CryptoFactoryC.bigIntToString(this.exponent), sb);
			sb.append("</exponent>");
		}
		sb.append("</petShare>");
	}

	public static PETShareC fromXML(Reader r) throws IOException {
		Util.swallowTag(r, "petShare");

		ElGamalCiphertextC ciphertext1 = null;
		ElGamalCiphertextC ciphertext2 = null;

		ciphertext1 = ElGamalCiphertextC.fromXML(r);
		ciphertext2 = ElGamalCiphertextC.fromXML(r);

		CivitasBigInteger exponent = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(r, "exponent")));

		Util.swallowEndTag(r, "petShare");

		return new PETShareC(ciphertext1, ciphertext2, exponent);
	}

	@Override
	@Deprecated
	public PETDecommitment decommitment(ElGamalParameters params) {
		return constructPETDecommitment.apply(params, exponent, ciphertext1,
				ciphertext2);
	}

}