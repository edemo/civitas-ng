package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.util.Tested;

public class VerifyElGamal1OfLReencryptionTest extends TestBase
		implements ElGamal1OfLReencryptionTestData {

	@Tested
	VerifyElGamal1OfLReencryption verifyElGamal1OfLReencryption;

	@Test
	@DisplayName("verify works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertTrue(verifyElGamal1OfLReencryption.apply(EL_GAMAL_1_OF_L_REENCRYPTION,
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

}
