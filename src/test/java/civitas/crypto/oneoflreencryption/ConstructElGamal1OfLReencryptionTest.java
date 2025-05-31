package civitas.crypto.oneoflreencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

public class ConstructElGamal1OfLReencryptionTest extends TestBase
		implements ElGamal1OfLReencryptionCTestData {

	@Tested
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;

	@Test
	@DisplayName("constructs an encrypted choice,"
			+ "containing the reencryption of the choosen vote and its proof ")
	void test() {
		ElGamal1OfLReencryption encChoice = constructElGamal1OfLReencryption
				.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST,
						NO_OF_WELL_KNOWN_CIPHERTEXTS, MY_CHOICE,
						ELGAMAL_REENCRYPT_FACTOR_E);
		assertEquals(EL_GAMAL_PROOF_1_OF_L, encChoice.proof);
		assertEquals(REENCRYPTED_WELL_KNOWN_CHOICE, encChoice.m);

	}
}
