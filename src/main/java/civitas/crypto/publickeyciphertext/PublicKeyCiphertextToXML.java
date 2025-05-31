/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickeyciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class PublicKeyCiphertextToXML implements Constants {

	public String apply(PublicKeyCiphertext that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PublicKeyCiphertext that, PrintWriter s) {
		s.print('<');
		s.print(PublicKeyCiphertextOPENING_TAG);
		s.print('>');
		Util.escapeString(
				java.util.Base64.getEncoder().encodeToString(that.encryptedBytes), s);
		s.print("</");
		s.print(PublicKeyCiphertextOPENING_TAG);
		s.print('>');
	}

}
