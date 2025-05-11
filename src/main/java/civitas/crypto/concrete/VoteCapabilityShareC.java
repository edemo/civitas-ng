/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoException;
import civitas.crypto.VoteCapabilityShare;
import civitas.util.CivitasBigInteger;

public class VoteCapabilityShareC extends ElGamalMsgC
		implements VoteCapabilityShare {

	public VoteCapabilityShareC(CivitasBigInteger c, ElGamalParametersC params)
			throws CryptoException {
		super(c, params);
	}

	public VoteCapabilityShareC(String c, ElGamalParametersC params)
			throws CryptoException {
		super(c, params);
	}

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
			Util.escapeString(CryptoFactoryC.bigIntToString(this.m), s);
		}
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	public static VoteCapabilityShare fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, OPENING_TAG));
		return new VoteCapabilityShareC(CryptoFactoryC.stringToBigInt(s));
	}

	public int intValue() throws NumberFormatException {
		return m.intValue();
	}

}
