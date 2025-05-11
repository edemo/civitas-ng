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
import civitas.crypto.VoteCapability;
import civitas.util.CivitasBigInteger;

public class VoteCapabilityC extends ElGamalMsgC implements VoteCapability {

	public VoteCapabilityC(CivitasBigInteger c, ElGamalParametersC params)
			throws CryptoException {
		super(c, params);
	}

	public VoteCapabilityC(String c, ElGamalParametersC params)
			throws CryptoException {
		super(c, params);
	}

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
			Util.escapeString(CryptoFactoryC.bigIntToString(this.m), s);
		}
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	public static VoteCapability fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, OPENING_TAG));
		return new VoteCapabilityC(CryptoFactoryC.stringToBigInt(s));
	}

	public int intValue() throws NumberFormatException {
		return m.intValue();
	}

}
