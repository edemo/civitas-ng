package civitas.crypto.ciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class ElGamalCiphertextToXML {

	public String apply(ElGamalCiphertext that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalCiphertext that, PrintWriter s) {
		s.print('<');
		s.print(Constants.ElGamalCiphertextOPENING_TAG);
		s.print('>');
		s.print("<a>");
		if (that.a != null)
			Util.escapeString(Util.fromBigInt(that.a), s);
		s.print("</a>");
		s.print("<b>");
		if (that.b != null)
			Util.escapeString(Util.fromBigInt(that.b), s);
		s.print("</b>");
		s.print("</");
		s.print(Constants.ElGamalCiphertextOPENING_TAG);
		s.print('>');
	}

}
