package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalKeyShareC;
import civitas.crypto.concrete.ElGamalKeyShareTestData;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.Tested;

public class VerifyElGamalKeyShareTest extends ConcreteTestBase
		implements ElGamalKeyShareTestData {
	@Tested
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	@Test
	@DisplayName("verify is true for a correctly constructed one")
	void test1() throws IllegalArgumentException, IOException {
		ElGamalKeyShareC share = EL_GAMAL_KEY_SHARE_E;
		assertTrue(verifyElGamalKeyShare.apply(share));
	}

	@Test
	@DisplayName("verify is false if the proof is null")
	void test1_1() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamalKeyShare
				.apply(new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY_EPRIME, null)));
	}

	@Test
	@DisplayName("verify is false if the key is null")
	void test1_2() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamalKeyShare
				.apply(new ElGamalKeyShareC(null, ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("verify is false if the key is not for the proof")
	void test1_3() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamalKeyShare.apply(new ElGamalKeyShareC(
				new ElGamalPublicKeyC(BIGINT_B, EL_GAMAL_PARAMETERS),
				ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

}
