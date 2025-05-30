/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapabilityshare;

import java.io.PrintWriter;

import civitas.common.Util;
import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;

public class VoteCapabilityShareC extends ElGamalMsg
		implements VoteCapabilityShare {

	public VoteCapabilityShareC(CivitasBigInteger c) {
		super(c);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VoteCapabilityShareC) {
			VoteCapabilityShareC that = (VoteCapabilityShareC) obj;
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
