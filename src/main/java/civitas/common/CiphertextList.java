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

import civitas.crypto.CryptoFactory;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalCiphertext;

/**
 * A ciphertext list is just a list of ciphertexts.
 */
public class CiphertextList implements XMLSerializable {
	public final static String META = "ciphertextList";

	public final ElGamalCiphertext[] ciphertexts;

	public static CryptoFactory factory;

	public CiphertextList(ElGamalCiphertext[] ciphertexts) {
		factory = CryptoUtil.factory();

		ElGamalCiphertext[] cs;
		if (ciphertexts != null) {
			cs = ciphertexts.clone();
		} else {
			cs = new ElGamalCiphertext[0];
		}
		this.ciphertexts = cs;
	}

	public CiphertextList(ElGamalCiphertext[] ciphertexts, boolean dummy) {
		factory = CryptoUtil.factory();

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
		int length = ciphertexts == null ? 0 : ciphertexts.length;
		sb.print(length);
		sb.print("</size>");
		for (int i = 0; i < length; i++) {
			ciphertexts[i].toXML(sb);
		}
		sb.print("</ciphertextList>");
	}

	public static CiphertextList fromXML(Reader r)
			throws IllegalArgumentException, IOException {

		Util.swallowTag(r, "ciphertextList");
		int size = Util.readSimpleIntTag(r, "size");
		ElGamalCiphertext[] ciphertexts = new ElGamalCiphertext[size < 0 ? 0
				: size];

		for (int i = 0; i < ciphertexts.length; i++) {
			ciphertexts[i] = factory.elGamalCiphertextFromXML(r);
		}

		Util.swallowEndTag(r, "ciphertextList");
		CiphertextList cl = new CiphertextList(ciphertexts);

		return cl;
	}
}