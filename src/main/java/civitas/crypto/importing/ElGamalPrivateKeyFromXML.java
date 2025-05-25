package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.util.Use;

public class ElGamalPrivateKeyFromXML {

	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;

	public ElGamalPrivateKeyC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalPrivateKey");
		Util.swallowTag(r, "params");
		ElGamalParameters params = elGamalParametersFromXML.apply(r);
		Util.swallowEndTag(r, "params");
		String x = Util.unescapeString(Util.readSimpleTag(r, "x"));
		Util.swallowEndTag(r, "elGamalPrivateKey");
		return new ElGamalPrivateKeyC(CryptoFactoryC.stringToBigInt(x), params);
	}

}
