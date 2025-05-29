package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.Tested;
import civitas.util.Use;

public class DecryptElGamalMessageTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {

	@Tested
	DecryptElGamalMessage decryptElGamalMessage;

	@Use
	SignAndEncrypt signAndEncrypt;

	@Use
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("elGamalDecrypt decrypts and verifies the siged cyphertext")
	void elGamalDecryptTest() throws Exception {

		ElGamalSignedCiphertextC encrypted = SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV;

		ElGamalMsgC decrypted = (ElGamalMsgC) decryptElGamalMessage
				.apply(ELGAMAL_PRIVATE_KEY_E, encrypted, ADDITIONALENV_BYTES);
		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED, decrypted.m);

	}

	@Test
	@DisplayName("elGamalDecrypt throws CryptoException when the verification fails")
	void elGamalDecryptTest1() throws Exception {

		byte[] env = SOMESTRING_EXTENDED.getBytes();
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS);

		ElGamalSignedCiphertextC encrypted = (ElGamalSignedCiphertextC) signAndEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg,
						new ElGamalReencryptFactorC(SOME_INT_BIG), env);

		assertThrows(CryptoException.class, () -> decryptElGamalMessage
				.apply(ELGAMAL_PRIVATE_KEY_E, encrypted, null));

	}

	@Test
	@DisplayName("elGamalDecrypt decrypts non-signed cyphertext correctly")
	void elGamalDecryptTest2() throws Exception {

		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED,
				((ElGamalMsgC) decryptElGamalMessage.apply(ELGAMAL_PRIVATE_KEY_E,
						CIPHERTEXT_E)).m);

	}

}
