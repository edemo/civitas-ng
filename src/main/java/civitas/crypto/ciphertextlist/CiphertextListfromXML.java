package civitas.crypto.ciphertextlist;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
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
		ElGamalCiphertext[] ciphertexts = new ElGamalCiphertext[size < 0 ? 0
				: size];

		for (int i = 0; i < ciphertexts.length; i++) {
			ciphertexts[i] = elGamalCiphertextFromXML.apply(r);
		}

		Util.swallowEndTag(r, "ciphertextList");
		CiphertextList cl = constructCiphertextList.apply(ciphertexts);

		return cl;
	}
}
