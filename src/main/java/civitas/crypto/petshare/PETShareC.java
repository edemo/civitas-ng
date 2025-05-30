/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petdecommitment.ConstructPETDecommitment;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class PETShareC implements PETShare {
	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;
	@Use
	ConstructPETDecommitment constructPETDecommitment;
	@Use
	static ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	public final ElGamalCiphertext ciphertext1;
	public final ElGamalCiphertext ciphertext2;

	public final CivitasBigInteger exponent;

	public PETShareC(ElGamalCiphertext ciphertext1, ElGamalCiphertext ciphertext2,
			CivitasBigInteger exponent) {
		this.ciphertext1 = ciphertext1;
		this.ciphertext2 = ciphertext2;
		this.exponent = exponent;
	}

	@Override
	public ElGamalCiphertext ciphertext1() {
		return ciphertext1;
	}

	@Override
	public ElGamalCiphertext ciphertext2() {
		return ciphertext2;
	}

	@Deprecated
	public ElGamalCiphertext ciphertextA() {
		return ciphertext1;
	}

	@Deprecated
	public ElGamalCiphertext ciphertextB() {
		return ciphertext2;
	}

	public CivitasBigInteger exponent() {
		return exponent;
	}

	@Override
	@Deprecated
	public PETDecommitment decommitment(ElGamalParameters params) {
		return constructPETDecommitment.apply(params, exponent, ciphertext1,
				ciphertext2);
	}

}