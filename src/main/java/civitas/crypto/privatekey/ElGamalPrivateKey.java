/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.privatekey;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ElGamalPrivateKey {

	public final CivitasBigInteger x;

	public final ElGamalParameters params;

	public ElGamalPrivateKey(CivitasBigInteger x, ElGamalParameters params) {
		this.x = x;
		this.params = params;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	public void toXML(PrintWriter s) {
		s.print("<elGamalPrivateKey>");

		s.print("<params>");
		if (this.params != null) {
			this.params.toXML(s);
		}
		s.print("</params>");
		s.print("<x>");
		if (this.x != null)
			Util.escapeString(Util.fromBigInt(x), s);
		s.print("</x>");

		s.print("</elGamalPrivateKey>");
	}

}
