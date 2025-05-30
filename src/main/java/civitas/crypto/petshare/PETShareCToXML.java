package civitas.crypto.petshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class PETShareCToXML {
	@Use
	static ElGamalCiphertextToXML elGamalCiphertextToXML;

	public String apply(PETShareC that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PETShareC that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.append("<petShare>");
		if (that.ciphertext1 != null) {
			elGamalCiphertextToXML.apply(that.ciphertext1, sb);
		}
		if (that.ciphertext2 != null) {
			elGamalCiphertextToXML.apply(that.ciphertext2, sb);
		}
		if (that.exponent != null) {
			sb.append("<exponent>");
			Util.escapeString(Util.fromBigInt(that.exponent), sb);
			sb.append("</exponent>");
		}
		sb.append("</petShare>");
	}

}
