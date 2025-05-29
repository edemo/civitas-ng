/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.io.PrintWriter;

import civitas.common.CiphertextList;

/**
 * Proof that a ciphertext is a re-encryption of some element from a set of
 * ciphertexts of size L.
 */
public interface ElGamal1OfLReencryption {
	public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts,
			int L);

	public ElGamalCiphertext getCiphertext();

	public ElGamalProof1OfL getProof();

	public void toXML(PrintWriter sb);

	public boolean equals(ElGamal1OfLReencryption r);
}
