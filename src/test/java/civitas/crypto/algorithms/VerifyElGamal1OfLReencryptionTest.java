package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamal1OfLReencryptionC;
import civitas.crypto.concrete.ElGamal1OfLReencryptionCTestData;
import civitas.util.Tested;

public class VerifyElGamal1OfLReencryptionTest extends ConcreteTestBase
		implements ElGamal1OfLReencryptionCTestData {

	@Tested
	VerifyElGamal1OfLReencryption verifyElGamal1OfLReencryption;

	@Test
	@DisplayName("verify works as expected")
	void test2() throws IllegalArgumentException, IOException {

		assertTrue(verifyElGamal1OfLReencryption.apply(EL_GAMAL_1_OF_L_REENCRYPTION,
				EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

	@Test
	@DisplayName("verify for null proof is false")
	void test2_1() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamal1OfLReencryption.apply(
				new ElGamal1OfLReencryptionC(CIPHERTEXT_E, null),
				EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

}
