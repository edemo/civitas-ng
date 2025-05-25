package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;

public class ElGamalReencryptFactorFromXML {

	public ElGamalReencryptFactor apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, "r"));
		return new ElGamalReencryptFactorC(CryptoFactoryC.stringToBigInt(s));
	}

}
