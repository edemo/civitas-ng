package civitas.crypto.oneoflreencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class ConstructElGamal1OfLReencryptionTest extends TestBase
		implements ElGamal1OfLReencryptionTestData {

	@InjectMocks
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;

	@Test
	@DisplayName("constructs an encrypted choice,"
			+ "containing the reencryption of the choosen vote and its proof ")
	void test() {
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption.apply(
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, MY_CHOICE.ordinal(),
				ELGAMAL_REENCRYPT_FACTOR_E);
		assertEquals(EL_GAMAL_PROOF_1_OF_L, encChoice.proof);
		assertEquals(REENCRYPTED_WELL_KNOWN_CHOICE, encChoice.m);
	}

	@Test
	@DisplayName("if choice > number of ciphertexts, a null is returned")
	void test1() {
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption.apply(
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, CIPHERTEXT_LIST.size(),
				ELGAMAL_REENCRYPT_FACTOR_E);
		assertNull(encChoice);
	}

	@Test
	@DisplayName("if ciphertexts is null, a null is returned")
	void test2() {
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption.apply(
				EL_GAMAL_PUBLIC_KEY_E, null, MY_CHOICE.ordinal(),
				ELGAMAL_REENCRYPT_FACTOR_E);
		assertNull(encChoice);
	}

}
