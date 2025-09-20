package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;
import io.github.magwas.testing.TestBase;

class VerifyElGamalSignatureTest extends TestBase implements ElGamalSignedCiphertextTestData, BallotDesignTestData {
	@InjectMocks
	VerifyElGamalSignature verifyElGamalSignature;

	@Test
	@DisplayName("elGamalVerify works as expected: "
			+ "c == hash(g^d * a^(-c), a, b, env) %q, where c and d are used mod q, others mod p")
	void elGamalVerifyTest() throws Exception {

		assertTrue(verifyElGamalSignature.apply(
				EL_GAMAL_PARAMETERS,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV,
				ADDITIONALENV_BYTES));
	}

	@Test
	@DisplayName("if the verification fails, false is returned")
	void test() throws Exception {

		assertFalse(verifyElGamalSignature.apply(
				EL_GAMAL_PARAMETERS,
				SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV_BAD,
				ADDITIONALENV_BYTES));
	}

	@Test
	@DisplayName("the version without env does the same without including env in the hash")
	void elGamalVerifyTest1() throws Exception {
		assertTrue(verifyElGamalSignature.apply(EL_GAMAL_PARAMETERS, SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0));
	}

	@Test
	@DisplayName("when the version without env fails false returned")
	void t1() throws Exception {
		assertFalse(verifyElGamalSignature.apply(
				EL_GAMAL_PARAMETERS, SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_BAD));
	}
}
