/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petdecommitment;

import civitas.crypto.Constants;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;

public class PETDecommitmentC implements PETDecommitment, Constants {
	public final CivitasBigInteger di;
	public final CivitasBigInteger ei;
	public final ElGamalProofDiscLogEquality proof;

	public PETDecommitmentC(CivitasBigInteger di, CivitasBigInteger ei,
			ElGamalProofDiscLogEquality proof) {
		this.di = di;
		this.ei = ei;
		this.proof = proof;
	}

}