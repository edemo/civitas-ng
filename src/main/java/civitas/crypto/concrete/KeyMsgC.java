/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.UnsupportedEncodingException;

public abstract class KeyMsgC {
	final String m;
	public final String CHARSET_NAME = "UTF-8";

	public KeyMsgC(String m) {
		this.m = m;
	}

	public KeyMsgC(byte[] plaintext) {
		try {
			m = new String(plaintext, CHARSET_NAME);
		} catch (UnsupportedEncodingException impossible) {
			throw new SecurityException(impossible);
		}

	}

	public byte[] toBytes() {
		try {
			return m.getBytes(CHARSET_NAME);
		} catch (UnsupportedEncodingException impossible) {
			throw new SecurityException(impossible);
		}
	}

	@Override
	public String toString() {
		return m;
	}

}
