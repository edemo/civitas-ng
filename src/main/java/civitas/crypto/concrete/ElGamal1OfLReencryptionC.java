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
import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalProof1OfL;
import civitas.crypto.ElGamalPublicKey;

public class ElGamal1OfLReencryptionC implements ElGamal1OfLReencryption {
	public final ElGamalCiphertextC m;
	public final ElGamalProof1OfLC proof;

	public ElGamal1OfLReencryptionC(ElGamalCiphertextC m,
			ElGamalProof1OfLC proof) {
		this.m = m;
		this.proof = proof;
	}

	@Override
	public ElGamalCiphertext getCiphertext() {
		return m;
	}

	@Override
	public ElGamalProof1OfL getProof() {
		return proof;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamal1OfLReencryption>");
		if (m != null)
			this.m.toXML(s);
		if (proof != null)
			this.proof.toXML(s);
		s.print("</elGamal1OfLReencryption>");
	}

	@Override
	@Deprecated
	public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts,
			int L) {
		throw new UnsupportedOperationException(
				"use VerifyElGamal1OfLReencryption");
	}

	@Override
	public boolean equals(ElGamal1OfLReencryption r) {
		if (r instanceof ElGamal1OfLReencryptionC) {
			ElGamal1OfLReencryptionC that = (ElGamal1OfLReencryptionC) r;
			try {
				return this.m.equals(that.m) && this.proof.equals(that.proof);
			} catch (NullPointerException e) {
				return false;
			}
		}
		return false;
	}
}
