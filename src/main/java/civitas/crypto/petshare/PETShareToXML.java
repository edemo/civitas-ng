package civitas.crypto.petshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class PETShareToXML {
	@Use
	static ElGamalCiphertextToXML elGamalCiphertextToXML;

	public String apply(PETShare that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PETShare that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.append("<petShare>");
		elGamalCiphertextToXML.apply(that.ciphertext1, sb);
		elGamalCiphertextToXML.apply(that.ciphertext2, sb);
		sb.append("<exponent>");
		Util.escapeString(Util.fromBigInt(that.exponent), sb);
		sb.append("</exponent>");
		sb.append("</petShare>");
	}

}
