package civitas.crypto.signedciphertext;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalSignedCiphertextToXML {

	public String apply(ElGamalSignedCiphertext that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalSignedCiphertext that, PrintWriter s) {
		s.print("<elGamalSignedCiphertext>");
		s.print("<a>");
		if (that.a != null)
			Util.escapeString(Util.fromBigInt(that.a), s);
		s.print("</a>");
		s.print("<b>");
		if (that.b != null)
			Util.escapeString(Util.fromBigInt(that.b), s);
		s.print("</b>");
		s.print("<c>");
		if (that.c != null)
			Util.escapeString(Util.fromBigInt(that.c), s);
		s.print("</c>");
		s.print("<d>");
		if (that.d != null)
			Util.escapeString(Util.fromBigInt(that.d), s);
		s.print("</d>");
		s.print("</elGamalSignedCiphertext>");
	}

}
