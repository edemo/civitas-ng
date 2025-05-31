package civitas.crypto.oneoflreencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.Use;

public class ConstructElGamal1OfLReencryption {

	@Use
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Use
	ElGamalReencrypt elGamalReencrypt;

	public ElGamal1OfLReencryption apply(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice,
			ElGamalReencryptFactor factor) {
		if (ciphertexts == null || choice >= L || L > ciphertexts.size()) {
			return null;
		}
		ElGamalCiphertext m = elGamalReencrypt.apply(key, ciphertexts.get(choice),
				factor);
		ElGamalProof1OfLC proof = constructElGamalProof1OfL.apply(
				key, ciphertexts, L, choice, m,
				(ElGamalReencryptFactor) factor);
		return new ElGamal1OfLReencryption(m, proof);
	}

}
