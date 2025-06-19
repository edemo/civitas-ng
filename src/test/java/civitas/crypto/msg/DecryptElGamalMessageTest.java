package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;
import civitas.crypto.signedciphertext.SignAndEncrypt;

public class DecryptElGamalMessageTest extends TestBase
		implements ElGamalCiphertextTestData, ElGamalSignedCiphertextTestData {

	@InjectMocks
	DecryptElGamalMessage decryptElGamalMessage;

	@Autowired
	SignAndEncrypt signAndEncrypt;

	@Autowired
	ElGamalEncrypt elGamalEncrypt;
	@Autowired
	EncodeMessage encodeMessage;

	@Test
	@DisplayName("elGamalDecrypt decrypts and verifies the siged cyphertext")
	void elGamalDecryptTest() throws Exception {

		ElGamalSignedCiphertext encrypted = SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV;

		ElGamalMsg decrypted = decryptElGamalMessage.apply(EL_GAMAL_PRIVATE_KEY_E,
				encrypted, ADDITIONALENV_BYTES);
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
