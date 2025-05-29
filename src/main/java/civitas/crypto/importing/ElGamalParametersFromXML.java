package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalParametersFromXML {
	@Use
	ConvertToBigInt convertToBigInt;

	public ElGamalParametersC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalParameters");
		String sp = Util.unescapeString(Util.readSimpleTag(r, "p"));
		String sq = Util.unescapeString(Util.readSimpleTag(r, "q"));
		String sg = Util.unescapeString(Util.readSimpleTag(r, "g"));
		Util.swallowEndTag(r, "elGamalParameters");
		CivitasBigInteger p = convertToBigInt.apply(sp);
		CivitasBigInteger q = convertToBigInt.apply(sq);
		CivitasBigInteger g = convertToBigInt.apply(sg);
		return new ElGamalParametersC(p, q, g);
	}

}
