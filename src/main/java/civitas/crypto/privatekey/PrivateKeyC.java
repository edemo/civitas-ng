/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.privatekey;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;

public class PrivateKeyC implements PrivateKey {
	public static final String OPENING_TAG = "privateKey";
	public final java.security.PrivateKey k;

	public PrivateKeyC(java.security.PrivateKey k) {
		this.k = k;
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
		byte[] bs = k.getEncoded();
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

}
