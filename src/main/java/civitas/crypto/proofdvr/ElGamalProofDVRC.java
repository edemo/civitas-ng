/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofdvr;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

public class ElGamalProofDVRC implements ElGamalProofDVR {

	public final ElGamalCiphertext e;
	public final ElGamalCiphertext eprime;
	public final CivitasBigInteger c;
	public final CivitasBigInteger w;
	public final CivitasBigInteger r;
	public final CivitasBigInteger u;

	public ElGamalProofDVRC(ElGamalCiphertext e, ElGamalCiphertext eprime,
			CivitasBigInteger c, CivitasBigInteger w, CivitasBigInteger r,
			CivitasBigInteger u) {
		this.e = e;
		this.eprime = eprime;
		this.c = c;
		this.w = w;
		this.r = r;
		this.u = u;
	}

	@Override
	public ElGamalCiphertext getE() {
		return e;
	}

	@Override
	public ElGamalCiphertext getEprime() {
		return eprime;
	}

	@Override
	public boolean verify(ElGamalPublicKey K, ElGamalPublicKey verifierKey) {
		throw new UnsupportedOperationException("use VerifyElGamalProofDVR");
	}

}
