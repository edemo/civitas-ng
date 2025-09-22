package civitas.common.encryptedchoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.ballot.BallotTestData;
import civitas.crypto.ciphertextlist.ElGamalCiphertextListTestData;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;

class EncryptChoiceTest extends RandomAwareTestBase
		implements ElGamalCiphertextListTestData, BallotTestData, ElGamal1OfLReencryptionTestData {

	@InjectMocks
	EncryptChoice encryptChoice;

	@DisplayName(
			"""
			encrypts the choice for the ballot
			- generates a random reencryption factor
			- constructs an 1 of L reeencryption using the choice in the ballot and the factor
			""")
	@Test
	void test() {
		EncryptedChoice actual = encryptChoice.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, BALLOT.matrix, 0);

		assertEquals(
				new EncryptedChoice(
						ELGAMAL_REENCRYPT_FACTOR_EPRIME, EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(BALLOT.matrix[0])),
				actual);

		verify(encryptChoice.generateElGamalReencryptFactor).apply(EL_GAMAL_PARAMETERS);
		verify(encryptChoice.constructElGamal1OfLReencryption)
				.apply(
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						BALLOT.matrix[0].ordinal(),
						ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	}
}
