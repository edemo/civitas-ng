package civitas.common;

import java.util.Map;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.ElGamalCiphertextListTestData;

public interface VoteChoiceTestData extends ElGamalCiphertextListTestData {
	Map<VoteChoice, ElGamalCiphertext> REENCRYPTED_CHOICE_MAP = ConstructTestData
			.constructTestData(CHOICES,
					(choice) -> new ElGamalCiphertext(
							CIPHERTEXT_LIST.get(choice.ordinal()).getA()
									.modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P),
							CIPHERTEXT_LIST.get(choice.ordinal()).getB()
									.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P)));

}
