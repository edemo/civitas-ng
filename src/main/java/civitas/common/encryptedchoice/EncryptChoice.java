package civitas.common.encryptedchoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoError;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ConstructElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;

@Service
public class EncryptChoice {

	@Autowired
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;
	@Autowired
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	public EncryptedChoice apply(ElGamalPublicKey key, CiphertextList ciphertexts,
			int[] cbMatrix, int pos) throws CryptoError {
		int choice = cbMatrix[pos];
		ElGamalReencryptFactor encChoiceFactor = generateElGamalReencryptFactor
				.apply(key.params);
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption
				.apply(key, ciphertexts, 4, choice, encChoiceFactor);
		return new EncryptedChoice(encChoiceFactor, encChoice);
	}

}
