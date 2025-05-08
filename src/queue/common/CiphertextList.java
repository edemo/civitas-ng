/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalCiphertext;

/**
 * A ciphertext list is just a list of ciphertexts.
 */
public class CiphertextList implements XMLSerializable {
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

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<ciphertextList>");
		sb.print("<size>");
		sb.print(ciphertexts == null ? 0 : ciphertexts.length);
		sb.print("</size>");
		try {
			for (int i = 0; i < ciphertexts.length; i++) {
				ciphertexts[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</ciphertextList>");
	}

	public static CiphertextList fromXML(Reader r) throws IllegalArgumentException, IOException {

		Util.swallowTag(r, "ciphertextList");
		int size = Util.readSimpleIntTag(r, "size");
		ElGamalCiphertext[] ciphertexts = new ElGamalCiphertext[size < 0 ? 0 : size];

		for (int i = 0; i < ciphertexts.length; i++) {
			try {
				ciphertexts[i] = CryptoUtil.factory().elGamalCiphertextFromXML(r);
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IOException("size does not agree with number of ciphertexts");
			}
		}

		Util.swallowEndTag(r, "ciphertextList");
		CiphertextList cl = new CiphertextList(ciphertexts);

		return cl;
	}
}