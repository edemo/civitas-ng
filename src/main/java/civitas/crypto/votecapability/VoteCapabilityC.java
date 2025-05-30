/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapability;

import java.io.PrintWriter;

import civitas.common.Util;
import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;

public class VoteCapabilityC extends ElGamalMsg implements VoteCapability {

	public VoteCapabilityC(CivitasBigInteger c) {
		super(c);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VoteCapabilityC) {
			VoteCapabilityC that = (VoteCapabilityC) obj;
			return super.equals(that);
		}
		return false;
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(OPENING_TAG);
		s.print('>');
		if (this.m != null) {
			Util.escapeString(Util.fromBigInt(this.m), s);
		}
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	public int intValue() throws NumberFormatException {
		return m.intValue();
	}

}
