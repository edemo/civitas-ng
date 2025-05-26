/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import javax.crypto.SecretKey;

import civitas.common.Util;
import civitas.crypto.SharedKey;

public class SharedKeyC implements SharedKey {
	public final SecretKey k;
	final String name;

	public SharedKeyC(SecretKey k, String name) {
		this.k = k;
		this.name = name;
	}

	public String name() {
		return name;
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
		s.print("<n>");
		s.print(name);
		s.print("</n>");
		s.print("<k>");
		byte[] bs = k.getEncoded();
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</k>");
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	@Override
	public void toWire(PrintWriter s) {
		s.print(name);
		s.print('\n');
		byte[] bs = k.getEncoded();
		String base64 = Base64.getEncoder().encodeToString(bs);
		s.print(base64);
		s.print('\n');
	}

}
