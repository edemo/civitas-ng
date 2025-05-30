/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.decriptionshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;

public class ElGamalDecryptionShareC implements ElGamalDecryptionShare {
	public final CivitasBigInteger ai;
	public final ElGamalProofDiscLogEqualityC proof;

	public ElGamalDecryptionShareC(CivitasBigInteger ai,
			ElGamalProofDiscLogEqualityC proof) {
		this.ai = ai;
		this.proof = proof;
	}

	@Override
	public ElGamalProofDiscLogEquality getProof() {
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
		s.print("<ai>");
		if (ai != null)
			Util.escapeString(Util.fromBigInt(ai), s);
		s.print("</ai>");
		if (proof != null)
			this.proof.toXML(s);
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

}
