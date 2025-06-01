package civitas.crypto.signedciphertext;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalSignedCiphertextFromXML {
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalSignedCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalSignedCiphertext");
		CivitasBigInteger a = null;
		String sa = Util.readSimpleTag(r, "a");
		if (sa != null && sa.length() > 0) {
			a = convertToBigInt.apply(Util.unescapeString(sa));
		}

		CivitasBigInteger b = null;
		String sb = Util.readSimpleTag(r, "b");
		if (sb != null && sb.length() > 0) {
			b = convertToBigInt.apply(Util.unescapeString(sb));
		}

		CivitasBigInteger c = null;
		String sc = Util.readSimpleTag(r, "c");
		if (sc != null && sc.length() > 0) {
			c = convertToBigInt.apply(Util.unescapeString(sc));
		}

		CivitasBigInteger d = null;
		String sd = Util.readSimpleTag(r, "d");
		if (sd != null && sd.length() > 0) {
			d = convertToBigInt.apply(Util.unescapeString(sd));
		}

		Util.swallowEndTag(r, "elGamalSignedCiphertext");
		return new ElGamalSignedCiphertext(a, b, c, d);
	}

}
