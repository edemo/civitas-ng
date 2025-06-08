package civitas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.util.DI;

public class ConstructTestData implements ElGamal1OfLReencryptionTestData {

	public static <I, O> Map<I, O> constructTestData(List<I> values,
			Function<I, O> konstruktor) {
		Map<I, O> map = new HashMap<>();
		for (I value : values) {
			map.put(value, konstruktor.apply(value));
		}
		return map;
	}

	Function<Integer, ElGamal1OfLReencryption> constructReencryption = new Function<Integer, ElGamal1OfLReencryption>() {
		@Override
		public ElGamal1OfLReencryption apply(Integer choice) {
			ElGamalCiphertext reencryptedChoice = REENCRYPTED_CHOICE_MAP.get(choice);

			ElGamalProof1OfL proof = DI.get(ConstructElGamalProof1OfL.class).apply(
					EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS,
					choice, reencryptedChoice, ELGAMAL_REENCRYPT_FACTOR_E);
			return new ElGamal1OfLReencryption(reencryptedChoice, proof);
		}
	};

	ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION_I_BEATS_J = constructReencryption
			.apply(CommonConstants.VOTE_CHOICE_I_BEATS_J);

	ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION_J_BEATS_I = constructReencryption
			.apply(CommonConstants.VOTE_CHOICE_J_BEATS_I);

	ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION_NEITHER_BEAT = constructReencryption
			.apply(CommonConstants.VOTE_CHOICE_NEITHER_BEAT);

}
