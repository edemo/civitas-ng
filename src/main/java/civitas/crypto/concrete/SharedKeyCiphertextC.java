/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import civitas.crypto.SharedKeyCiphertext;

public class SharedKeyCiphertextC extends KeyCiphertextC
		implements SharedKeyCiphertext {
	public SharedKeyCiphertextC(byte[] encrypted) {
		super(encrypted);
	}

	@Override
	String openingTag() {
		return OPENING_TAG;
	}

}
