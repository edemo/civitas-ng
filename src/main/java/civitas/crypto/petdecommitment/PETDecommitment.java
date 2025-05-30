/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petdecommitment;

import java.io.PrintWriter;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;

public interface PETDecommitment {
	public final static String OPENING_TAG = "petD";

	public void toXML(PrintWriter sb);

	ElGamalProofDiscLogEquality proof();

	/**
	 * Verify that the decommitment and the commitment agree
	 */
	@Deprecated
	public boolean verify(PETCommitment c, ElGamalParameters params,
			ElGamalCiphertext ciphertext1, ElGamalCiphertext ciphertext2);
}