package civitas.crypto.privatekey;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalPrivateKeyToXML {

	public String apply(ElGamalPrivateKey that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalPrivateKey that, PrintWriter s) {
		s.print('<');
		s.print("elGamalPrivateKey");
		s.print('>');

		s.print("<params>");
		if (that.params != null) {
			that.params.toXML(s);
		}
		s.print("</params>");
		s.print("<y>");
		if (that.x != null)
			Util.escapeString(Util.fromBigInt(that.x), s);
		s.print("</y>");

		s.print("</");
		s.print("elGamalPrivateKey");
		s.print('>');
	}

}
