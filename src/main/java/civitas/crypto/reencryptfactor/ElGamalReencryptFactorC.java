/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.reencryptfactor;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public class ElGamalReencryptFactorC implements ElGamalReencryptFactor {
	public final CivitasBigInteger r;

	public ElGamalReencryptFactorC(CivitasBigInteger r) {
		this.r = r;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<r>");
		if (this.r != null)
			Util.escapeString(Util.fromBigInt(this.r), s);
		s.print("</r>");
	}

}
