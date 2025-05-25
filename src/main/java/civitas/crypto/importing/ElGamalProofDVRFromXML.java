package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.util.CivitasBigInteger;

public class ElGamalProofDVRFromXML {

	public ElGamalProofDVR apply(Reader reader)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(reader, "elGamalProofDVR");
		ElGamalCiphertextC e = (ElGamalCiphertextC) CryptoFactoryC.singleton()
				.elGamalCiphertextFromXML(reader);
		ElGamalCiphertextC eprime = (ElGamalCiphertextC) CryptoFactoryC.singleton()
				.elGamalCiphertextFromXML(reader);
		CivitasBigInteger c = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "c")));
		CivitasBigInteger w = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "w")));
		CivitasBigInteger r = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "r")));
		CivitasBigInteger u = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "u")));

		Util.swallowEndTag(reader, "elGamalProofDVR");
		return new ElGamalProofDVRC(e, eprime, c, w, r, u);
	}

}
