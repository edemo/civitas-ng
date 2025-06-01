package civitas.crypto.ciphertext;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalCiphertextFromXML {

	@Use
	ElGamalSignedCiphertextFromXML elGamalSignedCiphertextFromXML;

	private ElGamalCiphertext fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, Constants.ElGamalCiphertextOPENING_TAG);
		CivitasBigInteger a = null;
		String sa = Util.unescapeString(Util.readSimpleTag(r, "a"));
		a = Util.asBigint(sa);

		CivitasBigInteger b = null;
		String sb = Util.unescapeString(Util.readSimpleTag(r, "b"));
		b = Util.asBigint(sb);

		Util.swallowEndTag(r, Constants.ElGamalCiphertextOPENING_TAG);
		return new ElGamalCiphertext(a, b);
	}

	public ElGamalCiphertext apply(Reader r) throws IOException {
		if (Util.isNextTag(r, Constants.ElGamalCiphertextOPENING_TAG)) {
			return fromXML(r);
		} else {
			return elGamalSignedCiphertextFromXML.apply(r);
		}
	}

}
