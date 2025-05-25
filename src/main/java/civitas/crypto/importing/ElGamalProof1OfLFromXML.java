package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.util.CivitasBigInteger;

public class ElGamalProof1OfLFromXML {

	public ElGamalProof1OfLC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProof1OfL");
		int L = Util.readSimpleIntTag(r, "size");
		CivitasBigInteger[] dvs = new CivitasBigInteger[L];
		CivitasBigInteger[] rvs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			dvs[i] = CryptoFactoryC
					.stringToBigInt(Util.unescapeString(Util.readSimpleTag(r, "dv")));
		}
		for (int i = 0; i < L; i++) {
			rvs[i] = CryptoFactoryC
					.stringToBigInt(Util.unescapeString(Util.readSimpleTag(r, "rv")));
		}

		Util.swallowEndTag(r, "elGamalProof1OfL");
		return new ElGamalProof1OfLC(L, dvs, rvs);
	}

}
