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
import civitas.crypto.PETCommitment;

/**
 * A teller's shares of Plaintext Equivalence Test (PET) commitments
 */
public class TabTellerPETShareCommitments implements XMLSerializable {
	public final static String META_PREFIX = "ttPETShareCommits:";

	public final int tellerIndex;

	// an array of the commitments
	public final PETCommitment[] commitments;

	public TabTellerPETShareCommitments(int tellerIndex, PETCommitment[] commitments) {
		this.tellerIndex = tellerIndex;
		this.commitments = commitments;
	}

	public void toXML(PrintWriter[] sb) {
		toXML(sb);
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<tabTellerPETShareCommitments>");

		sb.print("<index>");
		sb.print(this.tellerIndex);
		sb.print("</index>");
		if (this.commitments != null) {
			sb.print("<size>");
			sb.print(this.commitments.length);
			sb.print("</size>");
			for (int i = 0; i < commitments.length; i++) {
				try {
					PETCommitment c = commitments[i];
					if (c != null) {
						c.toXML(sb);
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		} else {
			sb.print("<size>0</size>");
		}

		sb.print("</tabTellerPETShareCommitments>");
	}

	public static TabTellerPETShareCommitments fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "tabTellerPETShareCommitments");
		int index = Util.readSimpleIntTag(r, "index");
		int size = Util.readSimpleIntTag(r, "size");
		PETCommitment[] commitments = new PETCommitment[size < 0 ? 0 : size];
		for (int i = 0; i < size; i++) {
			PETCommitment c = null;
			try {
				c = CryptoUtil.factory().petCommitmentFromXML(r);
			} catch (NullPointerException imposs) {
			}
			try {
				commitments[i] = c;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		Util.swallowEndTag(r, "tabTellerPETShareCommitments");
		return new TabTellerPETShareCommitments(index, commitments);
	}
}