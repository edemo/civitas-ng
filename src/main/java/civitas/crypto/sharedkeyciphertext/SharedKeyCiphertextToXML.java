/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.sharedkeyciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class SharedKeyCiphertextToXML {

	public String apply(SharedKeyCiphertext that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(SharedKeyCiphertext that, PrintWriter s) {
		s.print('<');
		s.print(SharedKeyCiphertext.OPENING_TAG);
		s.print('>');
		Util.escapeString(
				java.util.Base64.getEncoder().encodeToString(that.encryptedBytes), s);
		s.print("</");
		s.print(SharedKeyCiphertext.OPENING_TAG);
		s.print('>');
	}

}
