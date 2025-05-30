/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petdecommitment;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.petcommitment.VerifyPETDecommitment;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

/**
 * A server's decommitment for a PET share
 */
public class PETDecommitmentC implements PETDecommitment {
	@Use
	VerifyPETDecommitment verifyPETDecommitment;
	public final CivitasBigInteger di;
	public final CivitasBigInteger ei;
	public final ElGamalProofDiscLogEquality proof;

	public PETDecommitmentC(CivitasBigInteger di, CivitasBigInteger ei,
			ElGamalProofDiscLogEquality proof) {
		this.di = di;
		this.ei = ei;
		this.proof = proof;
	}

	// return proof of equality of logs of d_i and e_i
	@Override
	public ElGamalProofDiscLogEquality proof() {
		return proof;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(OPENING_TAG);
		s.print('>');
		s.print("<d>");
		if (di != null)
			Util.escapeString(Util.fromBigInt(this.di), s);
		s.print("</d>");
		s.print("<e>");
		if (ei != null)
			Util.escapeString(Util.fromBigInt(this.ei), s);
		s.print("</e>");
		s.print("<prf>");
		proof.toXML(s);
		s.print("</prf>");
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	@Override
	@Deprecated
	public boolean verify(PETCommitment c, ElGamalParameters params,
			ElGamalCiphertext ciphertext1, ElGamalCiphertext ciphertext2) {
		return verifyPETDecommitment.apply(this, c, params, ciphertext1,
				ciphertext2);
	}

}