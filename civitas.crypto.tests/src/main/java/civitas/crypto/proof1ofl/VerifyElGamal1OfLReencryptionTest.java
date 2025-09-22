package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import io.github.magwas.testing.TestBase;

class VerifyElGamal1OfLReencryptionTest extends TestBase
		implements ElGamal1OfLReencryptionTestData {

	@InjectMocks
	VerifyElGamal1OfLReencryption verifyElGamal1OfLReencryption;

	@Test
	@DisplayName("verifies the proof in teh Reencryption")
	void test1() {
		assertTrue(verifyElGamal1OfLReencryption.apply(EL_GAMAL_1_OF_L_REENCRYPTION,
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

	@Test
	@DisplayName("if the proof is bad, false is returned")
	void test2() {
		assertFalse(verifyElGamal1OfLReencryption.apply(
				EL_GAMAL_1_OF_L_REENCRYPTION, EL_GAMAL_PUBLIC_KEY_EPRIME,
				CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

}
