/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.oneoflreencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.crypto.publickey.ElGamalPublicKey;

public class ElGamal1OfLReencryptionC implements ElGamal1OfLReencryption {
	public final ElGamalCiphertext m;
	public final ElGamalProof1OfLC proof;

	public ElGamal1OfLReencryptionC(ElGamalCiphertext m,
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
