package civitas.crypto.oneoflreencryption;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.crypto.proof1ofl.ElGamalProof1OfLFromXML;
import civitas.util.Use;

public class ElGamal1OfLReencryptionFromXML {

	@Use
	ElGamalProof1OfLFromXML elGamalProof1OfLFromXML;
	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	public ElGamal1OfLReencryption apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamal1OfLReencryption");
		ElGamalCiphertext m = elGamalCiphertextFromXML.apply(r);
		ElGamalProof1OfLC proof = elGamalProof1OfLFromXML.apply(r);
		Util.swallowEndTag(r, "elGamal1OfLReencryption");
		return new ElGamal1OfLReencryption(m, proof);
	}

}
