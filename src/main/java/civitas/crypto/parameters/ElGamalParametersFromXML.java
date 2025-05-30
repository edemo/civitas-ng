package civitas.crypto.parameters;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalParametersFromXML {
	@Use
	ConvertToBigInt convertToBigInt;
	@Use
	CheckGroup checkGroup;

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
		ElGamalParametersC params = new ElGamalParametersC(p, q, g);
		checkGroup.apply(params);
		return params;
	}

}
