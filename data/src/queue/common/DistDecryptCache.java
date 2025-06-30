/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.crypto.ElGamalCiphertext;

/**
 * Cache information useful for distributed decryptions
 */
public interface DistDecryptCache {
	boolean hasDecryptShare(String meta, int block, int ballotIndex, int tellerIndex);

	TabTellerDistributedDecryptions getDecryptShare(String meta, int block, int ballotIndex, int tellerIndex);

	void setDecryptShare(String meta, int block, int ballotIndex, int tellerIndex, TabTellerDistributedDecryptions d);

	void setCiphertexts(String meta, int block, int ballotIndex, ElGamalCiphertext[] ciphertexts);

	ElGamalCiphertext[] getCiphertexts(String meta, int block, int ballotIndex);
}