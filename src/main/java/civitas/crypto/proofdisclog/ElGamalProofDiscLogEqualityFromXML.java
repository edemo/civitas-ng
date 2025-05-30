package civitas.crypto.proofdisclog;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.Use;

public class ElGamalProofDiscLogEqualityFromXML {

	@Use
	ConvertToBigInt convertToBigInt;

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
		return new ElGamalProofDiscLogEqualityC(convertToBigInt.apply(g1),
				convertToBigInt.apply(g2), convertToBigInt.apply(a),
				convertToBigInt.apply(v), convertToBigInt.apply(w),
				convertToBigInt.apply(b), convertToBigInt.apply(c),
				convertToBigInt.apply(rr));
	}

}
