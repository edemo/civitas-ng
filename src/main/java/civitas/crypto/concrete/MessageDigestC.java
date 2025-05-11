/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.UnsupportedEncodingException;

import civitas.crypto.MessageDigest;

public class MessageDigestC implements MessageDigest {
	private final java.security.MessageDigest md;

	MessageDigestC(java.security.MessageDigest md) {
		this.md = md;
	}

	@Override
	public byte[] digest() {
		return md.digest();
	}

	@Override
	public void update(byte[] bs) {
		if (bs == null)
			return;
		md.update(bs);
	}

	@Override
	public void update(byte[] bs, boolean constArray) {
		if (bs == null)
			return;
		md.update(bs);
	}

	@Override
	public void update(byte b) {
		md.update(b);
	}

	@Override
	public void update(int i) {
		byte[] dword = new byte[4];
		dword[0] = (byte) (i & 0x00FF);
		dword[1] = (byte) ((i >> 8) & 0x000000FF);
		dword[2] = (byte) ((i >> 16) & 0x000000FF);
		dword[3] = (byte) ((i >> 24) & 0x000000FF);
		md.update(dword);
	}

	@Override
	public void update(long i) {
		byte[] dword = new byte[8];
		dword[0] = (byte) (i & 0x00FF);
		dword[1] = (byte) ((i >> 8) & 0x000000FF);
		dword[2] = (byte) ((i >> 16) & 0x000000FF);
		dword[3] = (byte) ((i >> 24) & 0x000000FF);
		dword[4] = (byte) ((i >> 32) & 0x000000FF);
		dword[5] = (byte) ((i >> 40) & 0x000000FF);
		dword[6] = (byte) ((i >> 48) & 0x000000FF);
		dword[7] = (byte) ((i >> 56) & 0x000000FF);
		md.update(dword);
	}

	@Override
	public void update(String s) {
		if (s == null)
			return;
		try {
			update(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException impossible) {
			throw new SecurityException(impossible);
		}

	}

	@Override
	public void update(char[] cbuf, int off, int len) {
		update(new String(cbuf, off, len));
	}

}
