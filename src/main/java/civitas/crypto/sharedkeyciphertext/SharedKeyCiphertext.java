/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.sharedkeyciphertext;

public class SharedKeyCiphertext {
	public static final String OPENING_TAG = "sharedKeyCiphertext";
	public final byte[] encryptedBytes;

	public SharedKeyCiphertext(byte[] encrypted) {
		this.encryptedBytes = encrypted;
	}

}
