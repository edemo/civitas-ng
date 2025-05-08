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
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;

/**
 * A capability mix is a sequence of encrypted capability capabilities, that is
 * of <code>ElGamalCiphertext</code>s. It is both the input type and the output
 * type for the mixing of the elctoral roll performed by tabulation tellers.
 * 
 */
public class CapabilityMix extends Mix {
	private final static String META = "capabilityMix:";

	public static String meta(final ElectionDetails details, int block, int mixNumber, boolean rightMix) {
		String blockDesc = details == null ? "" : details.blockName(block);
		return META + blockDesc + ":" + mixNumber + (rightMix ? "R" : "L");
	}

	public ElGamalCiphertext[] capabilities;

	public CapabilityMix(int number) {
		this(number, null);
	}

	public CapabilityMix(int number, byte[] mixNonceHash) {
		super(number, mixNonceHash);
		this.capabilities = new ElGamalCiphertext[0];
	}

	@Override
	public Object get(int i) throws IndexOutOfBoundsException {
		try {
			return capabilities[i];
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	public ElGamalCiphertext getReencrypted(int i, ElGamalReencryptFactor factor, ElGamalPublicKey key)
			throws IndexOutOfBoundsException {

		ElGamalCiphertext[] capabilities = this.capabilities;
		int ii = i; // declassify because of the indexoutofbounds that may be thrown
		ElGamalCiphertext ci = capabilities == null ? null : capabilities[ii];
		try {
			return CryptoUtil.factory().elGamalReencrypt(key, ci, factor);
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void add(Object v, byte[] commitment) throws ClassCastException {
		addEncCapability((ElGamalCiphertext) v);
		addCommitment(commitment);
	}

	public void addEncCapability(ElGamalCiphertext v) {
		try {
			ElGamalCiphertext[] n = new ElGamalCiphertext[capabilities.length + 1];
			for (int i = 0; i < capabilities.length; i++) {
				try {
					n[i] = capabilities[i];
				} catch (NullPointerException ignore) {
				} catch (ArrayIndexOutOfBoundsException ignore) {
				}
			}
			n[capabilities.length] = v;
			this.capabilities = n;
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
	}

	@Override
	public int size() {
		ElGamalCiphertext[] vs = capabilities;
		return vs == null ? 0 : vs.length;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<capabilityMix>");
		sb.print("<number>");
		sb.print(number);
		sb.print("</number>");

		if (mixNonceHash != null) {
			sb.print("<nonceHash>");
			sb.print(Util.constBytesToString(mixNonceHash));
			sb.print("</nonceHash>");
		}

		sb.print("<capabilities>");
		try {
			for (int i = 0; i < capabilities.length; i++) {
				capabilities[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</capabilities>");

		sb.print("</capabilityMix>");
	}

	public static CapabilityMix fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "capabilityMix");
		int number = Util.readSimpleIntTag(r, "number");
		byte[] nonceHash = null;
		if (Util.isNextTag(r, "nonceHash")) {
			nonceHash = Util.stringToConstBytes(Util.readSimpleTag(r, "nonceHash"));
		}
		CapabilityMix vm = new CapabilityMix(number, nonceHash);

		Util.swallowTag(r, "capabilities");
		while (Util.isNextTag(r, ElGamalCiphertext.OPENING_TAG)) {
			try {
				vm.addEncCapability(CryptoUtil.factory().elGamalCiphertextFromXML(r));
			} catch (NullPointerException imposs) {
			}
		}

		Util.swallowEndTag(r, "capabilities");
		Util.swallowEndTag(r, "capabilityMix");
		return vm;
	}
}