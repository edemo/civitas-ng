package civitas.common.encryptedchoice.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballot.tests.BallotTestData;
import civitas.common.encryptedchoice.EncryptChoice;
import civitas.common.encryptedchoice.EncryptedChoice;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.ciphertextlist.tests.ElGamalCiphertextListTestData;
import civitas.crypto.oneoflreencryption.ConstructElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.tests.ElGamal1OfLReencryptionTestData;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;
import io.github.magwas.konveyor.testing.TestUtil;

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
	void test() throws IllegalAccessException {
		EncryptedChoice actual = encryptChoice.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, BALLOT.matrix, 0);

		assertEquals(
				new EncryptedChoice(
						ELGAMAL_REENCRYPT_FACTOR_EPRIME, EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(BALLOT.matrix[0])),
				actual);

		GenerateElGamalReencryptFactor generateElGamalReencryptFactor =
				TestUtil.dependency(encryptChoice, GenerateElGamalReencryptFactor.class);
		ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption =
				TestUtil.dependency(encryptChoice, ConstructElGamal1OfLReencryption.class);
		verify(generateElGamalReencryptFactor).apply(EL_GAMAL_PARAMETERS);
		verify(constructElGamal1OfLReencryption)
				.apply(
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						BALLOT.matrix[0].ordinal(),
						ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	}
}
