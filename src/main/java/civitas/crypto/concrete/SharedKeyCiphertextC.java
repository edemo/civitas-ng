/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.SharedKeyCiphertext;

public class SharedKeyCiphertextC extends KeyCiphertextC
		implements SharedKeyCiphertext {
	public SharedKeyCiphertextC(byte[] encrypted) {
		super(encrypted);
	}

	public static SharedKeyCiphertext fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, OPENING_TAG));
		return new SharedKeyCiphertextC(Base64.getDecoder().decode(s));
	}

	@Override
	String openingTag() {
		return OPENING_TAG;
	}

}
