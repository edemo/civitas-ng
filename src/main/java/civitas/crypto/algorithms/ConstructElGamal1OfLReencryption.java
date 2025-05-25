package civitas.crypto.algorithms;

import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.concrete.ElGamal1OfLReencryptionC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
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
		ElGamalCiphertextC m = (ElGamalCiphertextC) elGamalReencrypt.apply(key,
				ciphertexts[choice], factor);
		ElGamalProof1OfLC proof = constructElGamalProof1OfL.apply(
				(ElGamalPublicKeyC) key, ciphertexts, L, choice, m,
				(ElGamalReencryptFactorC) factor);
		return new ElGamal1OfLReencryptionC(m, proof);
	}

}
