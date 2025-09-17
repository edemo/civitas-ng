package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.crypto.CryptoException;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;
import io.github.magwas.testing.TestBase;

class DecryptElGamalMessageTest extends TestBase
		implements ElGamalSignedCiphertextTestData, BallotDesignTestData {

	@InjectMocks
	DecryptElGamalMessage decryptElGamalMessage;

	@Test
	@DisplayName("elGamalDecrypt decrypts and verifies the siged cyphertext")
	void elGamalDecryptTest() throws Exception {

		ElGamalMsg decrypted = decryptElGamalMessage.apply(EL_GAMAL_PRIVATE_KEY_E,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV,
				ADDITIONALENV_BYTES);
		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED, decrypted.m);

	}

	@Test
	@DisplayName("elGamalDecrypt throws CryptoException when the verification fails")
	void elGamalDecryptTest1() throws Exception {

		assertThrows(CryptoException.class, () -> decryptElGamalMessage
				.apply(EL_GAMAL_PRIVATE_KEY_E, EL_GAMAL_SIGNED_CIPHERTEXT, null));

	}

	@Test
	@DisplayName("elGamalDecrypt decrypts non-signed cyphertext correctly")
	void elGamalDecryptTest2() throws Exception {

		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED,
				decryptElGamalMessage.apply(EL_GAMAL_PRIVATE_KEY_E, CIPHERTEXT_E).m);

	}

}
