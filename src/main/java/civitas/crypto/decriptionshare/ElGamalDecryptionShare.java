/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.decriptionshare;

import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;

public class ElGamalDecryptionShare {
	public final static String OPENING_TAG = "elGamalDecryptionShare";

	public final CivitasBigInteger ai;
	public final ElGamalProofDiscLogEqualityC proof;

	public ElGamalDecryptionShare(CivitasBigInteger ai,
			ElGamalProofDiscLogEqualityC proof) {
		this.ai = ai;
		this.proof = proof;
	}

}
