package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;

public class ElGamalProofDiscLogEqualityFromXML {

	public ElGamalProofDiscLogEquality apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "egPrfKnwDscLog");
		String g1 = Util.unescapeString(Util.readSimpleTag(r, "g1"));
		String g2 = Util.unescapeString(Util.readSimpleTag(r, "g2"));
		String v = Util.unescapeString(Util.readSimpleTag(r, "v"));
		String w = Util.unescapeString(Util.readSimpleTag(r, "w"));
		String a = Util.unescapeString(Util.readSimpleTag(r, "a"));
		String b = Util.unescapeString(Util.readSimpleTag(r, "b"));
		String c = Util.unescapeString(Util.readSimpleTag(r, "c"));
		String rr = Util.unescapeString(Util.readSimpleTag(r, "r"));

		Util.swallowEndTag(r, "egPrfKnwDscLog");
		return new ElGamalProofDiscLogEqualityC(CryptoFactoryC.stringToBigInt(g1),
				CryptoFactoryC.stringToBigInt(g2), CryptoFactoryC.stringToBigInt(a),
				CryptoFactoryC.stringToBigInt(v), CryptoFactoryC.stringToBigInt(w),
				CryptoFactoryC.stringToBigInt(b), CryptoFactoryC.stringToBigInt(c),
				CryptoFactoryC.stringToBigInt(rr));
	}

}
