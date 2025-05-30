package civitas.crypto.ciphertextlist;

import java.io.PrintWriter;

import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class CiphertextListToXML {
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	public void apply(CiphertextList that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<ciphertextList>");
		sb.print("<size>");
		int length = that.ciphertexts == null ? 0 : that.ciphertexts.length;
		sb.print(length);
		sb.print("</size>");
		for (int i = 0; i < length; i++) {
			elGamalCiphertextToXML.apply(that.ciphertexts[i], sb);
		}
		sb.print("</ciphertextList>");
	}

}
