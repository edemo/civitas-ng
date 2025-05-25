package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class ElGamalParametersFromXML {

	public ElGamalParametersC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalParameters");
		String sp = Util.unescapeString(Util.readSimpleTag(r, "p"));
		String sq = Util.unescapeString(Util.readSimpleTag(r, "q"));
		String sg = Util.unescapeString(Util.readSimpleTag(r, "g"));
		Util.swallowEndTag(r, "elGamalParameters");
		CivitasBigInteger p = CryptoFactoryC.stringToBigInt(sp);
		CivitasBigInteger q = CryptoFactoryC.stringToBigInt(sq);
		CivitasBigInteger g = CryptoFactoryC.stringToBigInt(sg);
		return new ElGamalParametersC(p, q, g);
	}

}
