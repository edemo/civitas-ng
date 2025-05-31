package civitas.crypto.proof1ofl;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalProof1OfLFromXML {

	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalProof1OfL apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProof1OfL");
		int L = Util.readSimpleIntTag(r, "size");
		CivitasBigInteger[] dvs = new CivitasBigInteger[L];
		CivitasBigInteger[] rvs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			dvs[i] = convertToBigInt
					.apply(Util.unescapeString(Util.readSimpleTag(r, "dv")));
		}
		for (int i = 0; i < L; i++) {
			rvs[i] = convertToBigInt
					.apply(Util.unescapeString(Util.readSimpleTag(r, "rv")));
		}

		Util.swallowEndTag(r, "elGamalProof1OfL");
		return new ElGamalProof1OfL(L, dvs, rvs);
	}

}
