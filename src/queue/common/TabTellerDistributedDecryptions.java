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
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalProofDiscLogEquality;

/**
 * A teller's shares of distributed decryptions.
 */
public class TabTellerDistributedDecryptions implements XMLSerializable {
	public final static String META_PREFIX = "ttDistDecrypt:";

	public final int tellerIndex;

	// an array of the decrypts
	public final ElGamalDecryptionShare[] decrypts;

	// an array of the proofs
	public final ElGamalProofDiscLogEquality[] proofs;

	public TabTellerDistributedDecryptions(int tellerIndex, ElGamalDecryptionShare[] decrypts,
			ElGamalProofDiscLogEquality[] proofs) {
		this.tellerIndex = tellerIndex;

		ElGamalDecryptionShare ds[] = null;
		if (decrypts != null) {
			ds = decrypts.clone();
		}
		this.decrypts = ds;

		ElGamalProofDiscLogEquality ps[] = null;
		if (proofs != null) {
			ps = proofs.clone();
		}
		this.proofs = ps;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<tabTellerDistributedDecryptions>");

		sb.print("<index>");
		sb.print(this.tellerIndex);
		sb.print("</index>");
		if (this.decrypts != null) {
			sb.print("<size>");
			sb.print(this.decrypts.length);
			sb.print("</size>");
			for (int i = 0; i < decrypts.length; i++) {
				try {
					ElGamalDecryptionShare c = decrypts[i];
					if (c != null) {
						c.toXML(sb);
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");
		}

		if (this.proofs != null) {
			for (int i = 0; i < proofs.length; i++) {
				try {
					ElGamalProofDiscLogEquality p = proofs[i];
					if (p != null) {
						p.toXML(sb);
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}

		sb.print("</tabTellerDistributedDecryptions>");
	}

	public static TabTellerDistributedDecryptions fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "tabTellerDistributedDecryptions");
		int index = Util.readSimpleIntTag(r, "index");
		int size = Util.readSimpleIntTag(r, "size");

		ElGamalDecryptionShare[] decrypts = new ElGamalDecryptionShare[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			ElGamalDecryptionShare c = null;
			try {
				c = CryptoUtil.factory().decryptionShareFromXML(r);
				decrypts[i] = c;
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		// read in the proofs.
		ElGamalProofDiscLogEquality[] proofs = new ElGamalProofDiscLogEquality[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			try {
				ElGamalProofDiscLogEquality c = CryptoUtil.factory().elGamalProofDiscLogEqualityFromXML(r);
				proofs[i] = c;
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "tabTellerDistributedDecryptions");
		return new TabTellerDistributedDecryptions(index, decrypts, proofs);
	}
}