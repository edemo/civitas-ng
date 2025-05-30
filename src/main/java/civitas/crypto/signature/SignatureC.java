/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.signature;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;

public class SignatureC implements Signature {
	public final byte[] signature;

	public SignatureC(byte[] signature) {
		this.signature = signature;
	}

	public byte[] toBytes() {
		return signature;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print('<');
		s.print(Signature.OPENING_TAG);
		s.print('>');
		Util.escapeString(Base64.getEncoder().encodeToString(signature), s);
		s.print("</");
		s.print(Signature.OPENING_TAG);
		s.print('>');
	}

}
