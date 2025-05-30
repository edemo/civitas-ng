package civitas.crypto.proofdvr;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
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
