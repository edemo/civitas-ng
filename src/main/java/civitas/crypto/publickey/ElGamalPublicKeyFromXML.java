package civitas.crypto.publickey;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersFromXML;
import civitas.util.Use;

public class ElGamalPublicKeyFromXML implements Constants {
	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalPublicKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalPublicKeyOPENING_TAG);
		Util.swallowTag(r, "params");
		ElGamalParameters params = elGamalParametersFromXML.apply(r);
		Util.swallowEndTag(r, "params");
		String y = Util.unescapeString(Util.readSimpleTag(r, "y"));
		Util.swallowEndTag(r, ElGamalPublicKeyOPENING_TAG);
		return new ElGamalPublicKey(convertToBigInt.apply(y), params);
	}

}
