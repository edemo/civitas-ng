package civitas.crypto.oneoflreencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
import civitas.util.Use;

public class ConstructElGamal1OfLReencryption {

	@Use
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Use
	ElGamalReencrypt elGamalReencrypt;

	public ElGamal1OfLReencryption apply(ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, int L, int choice,
			ElGamalReencryptFactor factor) {
		if (ciphertexts == null || choice >= L || L > ciphertexts.length) {
			return null;
		}
		ElGamalCiphertext m = (ElGamalCiphertext) elGamalReencrypt.apply(key,
				ciphertexts[choice], factor);
		ElGamalProof1OfLC proof = constructElGamalProof1OfL.apply(
				(ElGamalPublicKeyC) key, ciphertexts, L, choice, m,
				(ElGamalReencryptFactorC) factor);
		return new ElGamal1OfLReencryptionC(m, proof);
	}

}
