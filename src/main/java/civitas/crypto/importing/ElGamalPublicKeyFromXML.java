package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.Use;

public class ElGamalPublicKeyFromXML {
	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalPublicKeyC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalPublicKey.EG_OPENING_TAG);
		Util.swallowTag(r, "params");
		ElGamalParameters params = elGamalParametersFromXML.apply(r);
		Util.swallowEndTag(r, "params");
		String y = Util.unescapeString(Util.readSimpleTag(r, "y"));
		Util.swallowEndTag(r, ElGamalPublicKey.EG_OPENING_TAG);
		return new ElGamalPublicKeyC(convertToBigInt.apply(y), params);
	}

}
