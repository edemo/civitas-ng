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
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.PETCommitment;
import civitas.util.CivitasBigInteger;

public class PETCommitmentC implements PETCommitment {
	public final CivitasBigInteger hash;

	public PETCommitmentC(CivitasBigInteger hash) {
		this.hash = hash;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(OPENING_TAG);
		s.print('>');

		if (hash != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.hash), s);

		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	public static PETCommitmentC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		String d = Util.unescapeString(Util.readSimpleTag(r, OPENING_TAG));
		return new PETCommitmentC(CryptoFactoryC.stringToBigInt(d));
	}

}