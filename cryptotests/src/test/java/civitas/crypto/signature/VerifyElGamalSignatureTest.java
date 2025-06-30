package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;

public class VerifyElGamalSignatureTest extends TestBase
		implements ElGamalSignedCiphertextTestData {
	@InjectMocks
	VerifyElGamalSignature verifyElGamalSignature;

	@Test
	@DisplayName("elGamalVerify works as expected: "
			+ "c == hash(g^d * a^(-c), a, b, env) %q, where c and d are used mod q, others mod p")
	void elGamalVerifyTest() throws Exception {

		assertTrue(verifyElGamalSignature.apply(EL_GAMAL_PARAMETERS,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV,
				ADDITIONALENV_BYTES));

	}

	@Test
	@DisplayName("the version without env does the same without including env in the hash")
	void elGamalVerifyTest1() throws Exception {
		assertTrue(verifyElGamalSignature.apply(EL_GAMAL_PARAMETERS,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0));

	}

}
