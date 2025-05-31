package civitas.crypto.publickey;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParametersToXML;
import civitas.util.Use;

public class ElGamalPublicKeyToXML {
	@Use
	ElGamalParametersToXML elGamalParametersToXML;

	public String apply(ElGamalPublicKey that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalPublicKey that, PrintWriter s) {
		s.print('<');
		s.print(ElGamalPublicKey.EG_OPENING_TAG);
		s.print('>');

		s.print("<params>");
		if (that.params != null) {
			elGamalParametersToXML.apply(that.params, s);
		}
		s.print("</params>");
		s.print("<y>");
		if (that.y != null)
			Util.escapeString(Util.fromBigInt(that.y), s);
		s.print("</y>");

		s.print("</");
		s.print(ElGamalPublicKey.EG_OPENING_TAG);
		s.print('>');
	}

}
