package civitas.common.encryptedchoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.VoteChoice;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ConstructElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;

@Controller
public class EncryptChoice {

	@Autowired
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;
	@Autowired
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	public EncryptedChoice apply(final ElGamalPublicKey key,
			final CiphertextList ciphertexts, final VoteChoice[] cbMatrix,
			final int pos) {
		VoteChoice choice = cbMatrix[pos];
		ElGamalReencryptFactor encChoiceFactor = generateElGamalReencryptFactor
				.apply(key.params);
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption
				.apply(key, ciphertexts, choice.ordinal(), encChoiceFactor);
		return new EncryptedChoice(encChoiceFactor, encChoice);
	}

}
