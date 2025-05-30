/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickeyciphertext;

public class PublicKeyCiphertext {
	public static final String OPENING_TAG = "publicKeyCiphertext";

	public final byte[] encryptedBytes;

	public PublicKeyCiphertext(byte[] encrypted) {
		this.encryptedBytes = encrypted;
	}

}
