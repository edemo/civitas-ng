package civitas.crypto.reencryptfactor;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.Use;

public class ElGamalReencryptFactorFromXML {
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalReencryptFactor apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, "r"));
		return new ElGamalReencryptFactor(convertToBigInt.apply(s));
	}

}
