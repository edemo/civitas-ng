package civitas.crypto.algorithms;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;

public class ElGamalSignedCiphertextFromXML {

	public ElGamalSignedCiphertextC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalSignedCiphertext");
		CivitasBigInteger a = null;
		String sa = Util.readSimpleTag(r, "a");
		if (sa != null && sa.length() > 0) {
			a = CryptoFactoryC.stringToBigInt(Util.unescapeString(sa));
		}

		CivitasBigInteger b = null;
		String sb = Util.readSimpleTag(r, "b");
		if (sb != null && sb.length() > 0) {
			b = CryptoFactoryC.stringToBigInt(Util.unescapeString(sb));
		}

		CivitasBigInteger c = null;
		String sc = Util.readSimpleTag(r, "c");
		if (sc != null && sc.length() > 0) {
			c = CryptoFactoryC.stringToBigInt(Util.unescapeString(sc));
		}

		CivitasBigInteger d = null;
		String sd = Util.readSimpleTag(r, "d");
		if (sd != null && sd.length() > 0) {
			d = CryptoFactoryC.stringToBigInt(Util.unescapeString(sd));
		}

		Util.swallowEndTag(r, "elGamalSignedCiphertext");
		return new ElGamalSignedCiphertextC(a, b, c, d);
	}

}
