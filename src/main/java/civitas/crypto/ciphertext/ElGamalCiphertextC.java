/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.ciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public class ElGamalCiphertextC implements ElGamalCiphertext {
	public final CivitasBigInteger a;
	public final CivitasBigInteger b;

	public ElGamalCiphertextC(CivitasBigInteger a, CivitasBigInteger b) {
		this.a = a;
		this.b = b;
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
		s.print("<a>");
		if (a != null)
			Util.escapeString(Util.fromBigInt(this.a), s);
		s.print("</a>");
		s.print("<b>");
		if (b != null)
			Util.escapeString(Util.fromBigInt(this.b), s);
		s.print("</b>");
		s.print("</");
		s.print(OPENING_TAG);
		s.print('>');
	}

	@Override
	@Deprecated
	public void toUnsignedCiphertextXML(PrintWriter s) {
		toXML(s);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ElGamalCiphertextC)) {
			return false;
		}
		ElGamalCiphertextC x = (ElGamalCiphertextC) o;

		if (a == null)
			if (x.a == null)
				if (b == null)
					if (x.b == null)
						return true;
					else
						return false;
				else
					return b.equals(x.b);
			else
				return false;
		else if (a.equals(x.a))
			if (b == null)
				if (x.b == null)
					return true;
				else
					return false;
			else if (b.equals(x.b))
				return true;
			else
				return false;
		else
			return false;

	}

	@Override
	public boolean equals(ElGamalCiphertext c) {
		return equals((Object) c);
	}

	@Override
	public int hashCode() {
		return a.hashCode() ^ b.hashCode();
	}
}
