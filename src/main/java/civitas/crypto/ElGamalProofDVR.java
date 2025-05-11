/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.io.PrintWriter;

/**
 * Designated Verifier Re-encryption Proof
 */
public interface ElGamalProofDVR {
	public boolean verify(ElGamalPublicKey K, ElGamalPublicKey verifierKey);

	// getters
	ElGamalCiphertext getE();

	ElGamalCiphertext getEprime();

	public void toXML(PrintWriter sb);
}
