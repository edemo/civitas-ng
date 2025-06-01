package civitas.crypto.ciphertextlist;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.util.Use;

public class CiphertextListfromXML {
	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;
	@Use
	ConstructCiphertextList constructCiphertextList;

	public CiphertextList apply(Reader r)
			throws IllegalArgumentException, IOException {

		Util.swallowTag(r, "ciphertextList");
		int size = Util.readSimpleIntTag(r, "size");
		CiphertextList ciphertexts = new CiphertextList();

		for (int i = 0; i < size; i++) {
			ciphertexts.add(elGamalCiphertextFromXML.apply(r));
		}

		Util.swallowEndTag(r, "ciphertextList");

		return ciphertexts;
	}
}
