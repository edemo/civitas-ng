package civitas.crypto.oneoflreencryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;

@Controller
public class ConstructElGamal1OfLReencryption {

	@Autowired
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public ElGamal1OfLReencryption apply(ElGamalPublicKey key,
			CiphertextList ciphertexts, int choice, ElGamalReencryptFactor factor) {
		if (ciphertexts == null || choice >= ciphertexts.size()) {
			return null;
		}
		ElGamalCiphertextish m = elGamalReencrypt.apply(key,
				ciphertexts.get(choice), factor);
		ElGamalProof1OfL proof = constructElGamalProof1OfL.apply(key, ciphertexts,
				choice, m, factor);
		return new ElGamal1OfLReencryption(m, proof);
	}

}
