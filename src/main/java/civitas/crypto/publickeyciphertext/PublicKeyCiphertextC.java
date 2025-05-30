/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickeyciphertext;

import civitas.crypto.ciphertext.KeyCiphertextC;

public class PublicKeyCiphertextC extends KeyCiphertextC
		implements PublicKeyCiphertext {
	public PublicKeyCiphertextC(byte[] encrypted) {
		super(encrypted);
	}

	@Override
	public String openingTag() {
		return OPENING_TAG;
	}

}
