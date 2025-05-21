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
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.crypto.ElGamalPublicKey;
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

	@Override
	public boolean verify(ElGamalCiphertext c, ElGamalPublicKey K) {
		if (proof != null) {
			try {
				ElGamalCiphertextC cipher = (ElGamalCiphertextC) c;
				ElGamalPublicKeyC KC = (ElGamalPublicKeyC) K;
				ElGamalParametersC params = (ElGamalParametersC) K.getParams();

				if (proof.g1.equals(cipher.a) && proof.g2.equals(params.g)
						&& proof.v.equals(ai) && proof.w.equals(KC.y)) {
					return proof.verify(params);
				}
			} catch (NullPointerException e) {
				throw new Error(e);
			} catch (ClassCastException e) {
				throw new Error(e);
			}
		}

		return false;
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
			Util.escapeString(CryptoFactoryC.bigIntToString(ai), s);
		s.print("</ai>");
		if (proof != null)
			this.proof.toXML(s);
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	public static ElGamalDecryptionShareC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, OPENING_TAG);

		String sa = Util.unescapeString(Util.readSimpleTag(r, "ai"));
		CivitasBigInteger ai = CryptoFactoryC.stringToBigInt(sa);

		ElGamalProofDiscLogEqualityC proof = (ElGamalProofDiscLogEqualityC) CryptoFactoryC
				.singleton().elGamalProofDiscLogEqualityFromXML(r);

		Util.swallowEndTag(r, OPENING_TAG);
		return new ElGamalDecryptionShareC(ai, proof/* , params */);
	}
}
