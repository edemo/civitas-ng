package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalProofDVRFromXML {
	@Use
	ConvertToBigInt convertToBigInt;
	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	public ElGamalProofDVR apply(Reader reader)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(reader, "elGamalProofDVR");
		ElGamalCiphertextC e = elGamalCiphertextFromXML.apply(reader);
		ElGamalCiphertextC eprime = elGamalCiphertextFromXML.apply(reader);
		CivitasBigInteger c = convertToBigInt
				.apply(Util.unescapeString(Util.readSimpleTag(reader, "c")));
		CivitasBigInteger w = convertToBigInt
				.apply(Util.unescapeString(Util.readSimpleTag(reader, "w")));
		CivitasBigInteger r = convertToBigInt
				.apply(Util.unescapeString(Util.readSimpleTag(reader, "r")));
		CivitasBigInteger u = convertToBigInt
				.apply(Util.unescapeString(Util.readSimpleTag(reader, "u")));

		Util.swallowEndTag(reader, "elGamalProofDVR");
		return new ElGamalProofDVRC(e, eprime, c, w, r, u);
	}

}
