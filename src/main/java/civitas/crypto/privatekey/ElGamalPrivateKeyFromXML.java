package civitas.crypto.privatekey;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersFromXML;
import civitas.util.Use;

public class ElGamalPrivateKeyFromXML {

	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalPrivateKeyC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalPrivateKey");
		Util.swallowTag(r, "params");
		ElGamalParameters params = elGamalParametersFromXML.apply(r);
		Util.swallowEndTag(r, "params");
		String x = Util.unescapeString(Util.readSimpleTag(r, "x"));
		Util.swallowEndTag(r, "elGamalPrivateKey");
		return new ElGamalPrivateKeyC(convertToBigInt.apply(x), params);
	}

}
