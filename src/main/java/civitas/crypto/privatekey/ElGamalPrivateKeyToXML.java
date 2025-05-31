package civitas.crypto.privatekey;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersToXML;
import civitas.util.Use;

public class ElGamalPrivateKeyToXML {

	@Use
	ElGamalParametersToXML elGamalParametersToXML;

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
			elGamalParametersToXML.apply((ElGamalParameters) that.params, s);
		}
		s.print("</params>");
		s.print("<x>");
		if (that.x != null)
			Util.escapeString(Util.fromBigInt(that.x), s);
		s.print("</x>");

		s.print("</");
		s.print("elGamalPrivateKey");
		s.print('>');
	}

}
