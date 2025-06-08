package civitas.crypto.ciphertextlist;

import java.io.PrintWriter;
import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class CiphertextListToXML {
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	public void apply(CiphertextList that, PrintWriter sb) {
		if (sb == null)
			return;
		List<String> pieces = that.stream().map(ciphertext -> {
			if (null != ciphertext)
				return elGamalCiphertextToXML.apply(ciphertext);
			return (String) null;
		}).filter(x -> x != null).toList();
		sb.print("<ciphertextList>");
		sb.print("<size>");
		int length = pieces.size();
		sb.print(length);
		sb.print("</size>");
		pieces.forEach(s -> sb.print(s));
		sb.print("</ciphertextList>");
	}

}
