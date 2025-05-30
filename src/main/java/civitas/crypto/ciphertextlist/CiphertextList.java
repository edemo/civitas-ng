/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.ciphertextlist;

import civitas.crypto.ciphertext.ElGamalCiphertext;

/**
 * A ciphertext list is just a list of ciphertexts.
 */
public class CiphertextList {
	public final static String META = "ciphertextList";

	public final ElGamalCiphertext[] ciphertexts;

	public CiphertextList(ElGamalCiphertext[] ciphertexts) {

		ElGamalCiphertext[] cs;
		if (ciphertexts != null) {
			cs = ciphertexts.clone();
		} else {
			cs = new ElGamalCiphertext[0];
		}
		this.ciphertexts = cs;
	}

	public CiphertextList(ElGamalCiphertext[] ciphertexts, boolean dummy) {
		this.ciphertexts = ciphertexts;
	}

	public ElGamalCiphertext get(int i) throws IndexOutOfBoundsException {
		try {
			return ciphertexts[i];
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	public int size() {
		return ciphertexts == null ? 0 : ciphertexts.length;
	}

}