package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.concrete.ElGamalProofKnowDiscLogC;
import civitas.util.Use;

public class ElGamalProofKnowDiscLogFromXML {

	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalProofKnowDiscLogC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProofKnowDiscLog");
		String a = Util.unescapeString(Util.readSimpleTag(r, "a"));
		String c = Util.unescapeString(Util.readSimpleTag(r, "c"));
		String rr = Util.unescapeString(Util.readSimpleTag(r, "r"));
		String v = Util.unescapeString(Util.readSimpleTag(r, "v"));

		Util.swallowEndTag(r, "elGamalProofKnowDiscLog");
		return new ElGamalProofKnowDiscLogC(convertToBigInt.apply(a),
				convertToBigInt.apply(c), convertToBigInt.apply(rr),
				convertToBigInt.apply(v));
	}

}
