/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.ciphertextlist;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;

public class CiphertextList extends ArrayList<ElGamalCiphertextish> {
	private static final long serialVersionUID = 1L;

	public CiphertextList(final List<ElGamalCiphertext> list) {
		super(list);
	}

	public CiphertextList() {
		super();
	}
}