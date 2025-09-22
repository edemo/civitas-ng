package civitas.crypto.keyshare;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.keys.ElGamalKeyShareTestData;
import civitas.crypto.publickey.ElGamalPublicKey;
import io.github.magwas.testing.TestBase;

class VerifyElGamalKeyShareTest extends TestBase
		implements ElGamalKeyShareTestData {
	@InjectMocks
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	@Test
	@DisplayName("verify is true for a correctly constructed one")
	void test1() {
		ElGamalKeyShare share = EL_GAMAL_KEY_SHARE_E;
		assertTrue(verifyElGamalKeyShare.apply(share));
	}

	@Test
	@DisplayName("verify is false if the key is not for the proof")
	void test1_3() {
		assertFalse(verifyElGamalKeyShare.apply(
				new ElGamalKeyShare(new ElGamalPublicKey(BIGINT_B, EL_GAMAL_PARAMETERS),
						EL_GAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("verify is false if the proof fails")
	void test1_4() {
		assertFalse(verifyElGamalKeyShare.apply(new ElGamalKeyShare(
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PROOF_KNOWN_DISC_LOG_BAD)));
	}

}
