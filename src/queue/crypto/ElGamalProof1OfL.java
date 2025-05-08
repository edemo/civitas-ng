/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto;

import java.io.*;

import civitas.common.CiphertextList;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPublicKey;

/**
 * Proof that a ciphertext is a re-encryption of some element from a set of ciphertexts of size L.
 */
public interface ElGamalProof1OfL {
    public boolean verify(ElGamalPublicKey{} pubKey, CiphertextList{} ciphertexts, int{} L, ElGamalCiphertext{} msg);
    public void toXML (  PrintWriter   sb) where   <= lbl;
    public boolean equals(ElGamalProof1OfL p);
}
