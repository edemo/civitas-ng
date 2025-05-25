package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalProofKnowDiscLogC;

public class ElGamalProofKnowDiscLogFromXML {

	public ElGamalProofKnowDiscLogC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProofKnowDiscLog");
		String a = Util.unescapeString(Util.readSimpleTag(r, "a"));
		String c = Util.unescapeString(Util.readSimpleTag(r, "c"));
		String rr = Util.unescapeString(Util.readSimpleTag(r, "r"));
		String v = Util.unescapeString(Util.readSimpleTag(r, "v"));

		Util.swallowEndTag(r, "elGamalProofKnowDiscLog");
		return new ElGamalProofKnowDiscLogC(CryptoFactoryC.stringToBigInt(a),
				CryptoFactoryC.stringToBigInt(c), CryptoFactoryC.stringToBigInt(rr),
				CryptoFactoryC.stringToBigInt(v));
	}

}
