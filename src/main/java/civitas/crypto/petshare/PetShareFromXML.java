package civitas.crypto.petshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class PetShareFromXML {

	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	public PETShareC apply(Reader r) throws IOException {
		Util.swallowTag(r, "petShare");

		ElGamalCiphertext ciphertext1 = null;
		ElGamalCiphertext ciphertext2 = null;

		ciphertext1 = elGamalCiphertextFromXML.apply(r);
		ciphertext2 = elGamalCiphertextFromXML.apply(r);

		CivitasBigInteger exponent = Util
				.asBigint(Util.unescapeString(Util.readSimpleTag(r, "exponent")));

		Util.swallowEndTag(r, "petShare");

		return new PETShareC(ciphertext1, ciphertext2, exponent);
	}

}
