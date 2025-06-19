package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.messagedigest.CryptoHash;

public class VerifyElGamalSignatureTest extends TestBase
		implements ElGamalCiphertextTestData {
	@InjectMocks
	VerifyElGamalSignature verifyElGamalSignature;

	@Autowired
	CryptoHash cryptoHash;

	@Test
	@DisplayName("elGamalVerify works as expected: "
			+ "c == hash(g^d * a^(-c), a, b, env) %q, where c and d are used mod q, others mod p")
	void elGamalVerifyTest() throws Exception {

		assertTrue(verifyElGamalSignature.apply(EL_GAMAL_PARAMETERS,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV,
				ADDITIONALENV_BYTES));

	}

}
