package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalCiphertextFromXML {

	@Use
	ElGamalSignedCiphertextFromXML elGamalSignedCiphertextFromXML;

	public static ElGamalCiphertextC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalCiphertext.OPENING_TAG);
		CivitasBigInteger a = null;
		String sa = Util.unescapeString(Util.readSimpleTag(r, "a"));
		if (sa.length() > 0) {
			a = CryptoFactoryC.stringToBigInt(sa);
		}

		CivitasBigInteger b = null;
		String sb = Util.unescapeString(Util.readSimpleTag(r, "b"));
		if (sb.length() > 0) {
			b = CryptoFactoryC.stringToBigInt(sb);
		}

		Util.swallowEndTag(r, ElGamalCiphertext.OPENING_TAG);
		return new ElGamalCiphertextC(a, b);
	}

	public ElGamalCiphertextC apply(Reader r) throws IOException {
		if (Util.isNextTag(r, ElGamalCiphertext.OPENING_TAG)) {
			return fromXML(r);
		} else {
			return elGamalSignedCiphertextFromXML.apply(r);
		}
	}

}
