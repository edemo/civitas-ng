package civitas.crypto.reencryptfactor;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;

public class ElGamalReencryptFactorFromXML {

	public ElGamalReencryptFactor apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, "r"));
		return new ElGamalReencryptFactorC(CryptoFactoryC.stringToBigInt(s));
	}

}
