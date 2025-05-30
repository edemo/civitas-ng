/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petdecommitment.PETDecommitment;

/**
 * A server's share of the info needed for a distrubted Plaintext Eqiuvalence
 * Test.
 */
public interface PETShare {

	ElGamalCiphertext ciphertext1();

	ElGamalCiphertext ciphertext2();

	@Deprecated
	PETDecommitment decommitment(ElGamalParameters params);

}