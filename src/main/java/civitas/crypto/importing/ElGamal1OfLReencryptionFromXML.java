package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.concrete.ElGamal1OfLReencryptionC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.util.Use;

public class ElGamal1OfLReencryptionFromXML {

	@Use
	ElGamalProof1OfLFromXML elGamalProof1OfLFromXML;
	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	public ElGamal1OfLReencryption apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamal1OfLReencryption");
		ElGamalCiphertextC m = elGamalCiphertextFromXML.apply(r);
		ElGamalProof1OfLC proof = elGamalProof1OfLFromXML.apply(r);
		Util.swallowEndTag(r, "elGamal1OfLReencryption");
		return new ElGamal1OfLReencryptionC(m, proof);
	}

}
