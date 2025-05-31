package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyElGamalSignatureTest extends TestBase
		implements ElGamalCiphertextCTestData {
	@Tested
	VerifyElGamalSignature verifyElGamalSignature;

	@Use
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
