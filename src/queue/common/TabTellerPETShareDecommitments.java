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
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.crypto.PETDecommitment;

/**
 * A teller's shares of Plaintext Equivalence Test (PET) decommitments.
 */
public class TabTellerPETShareDecommitments implements XMLSerializable {
	public final static String META_PREFIX = "ttPETShareDecommits:";

	public final int tellerIndex;

	// an array of the decommitments
	public final PETDecommitment[] decommitments;

	// an array of the proofs
	public final ElGamalProofDiscLogEquality[] proofs;

	public TabTellerPETShareDecommitments(int tellerIndex, PETDecommitment[] decommitments,
			ElGamalProofDiscLogEquality[] proofs) {
		this.tellerIndex = tellerIndex;
		this.decommitments = decommitments;
		this.proofs = proofs;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<tabTellerPETShareDecommitments>");

		sb.print("<index>");
		sb.print(this.tellerIndex);
		sb.print("</index>");
		if (this.decommitments != null) {
			sb.print("<size>");
			sb.print(this.decommitments.length);
			sb.print("</size>");
			for (int i = 0; i < decommitments.length; i++) {
				try {
					PETDecommitment c = decommitments[i];
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

		sb.print("</tabTellerPETShareDecommitments>");
	}

	public static TabTellerPETShareDecommitments fromXML(Reader r) throws IllegalArgumentException, IOException

	{
		Util.swallowTag(r, "tabTellerPETShareDecommitments");
		int index = Util.readSimpleIntTag(r, "index");
		int size = Util.readSimpleIntTag(r, "size");
		PETDecommitment[] decommitments = new PETDecommitment[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			PETDecommitment c = null;
			try {
				c = CryptoUtil.factory().petDecommitmentFromXML(r);
			} catch (NullPointerException imposs) {
			}
			try {
				decommitments[i] = c;
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

		Util.swallowEndTag(r, "tabTellerPETShareDecommitments");
		return new TabTellerPETShareDecommitments(index, decommitments, proofs);
	}
}