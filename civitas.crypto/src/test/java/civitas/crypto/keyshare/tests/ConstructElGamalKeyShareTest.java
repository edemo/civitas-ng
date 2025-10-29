package civitas.crypto.keyshare.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoError;
import civitas.crypto.keypairshare.tests.ElGamalKeyPairShareTestData;
import civitas.crypto.keys.tests.ElGamalKeyShareTestData;
import civitas.crypto.keyshare.ConstructElGamalKeyShare;
import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.keyshare.VerifyElGamalKeyShare;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

class ConstructElGamalKeyShareTest extends TestBase implements ElGamalKeyPairShareTestData, ElGamalKeyShareTestData {

	@InjectMocks
	ConstructElGamalKeyShare constructElGamalKeyShare;

	@Test
	@DisplayName("the one-parameter version returns the public key with the proof of knowledge for the private key")
	void test() {
		ElGamalKeyShare actual = constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE);
		assertEquals(EL_GAMAL_KEY_SHARE_E, actual);
	}

	@Test
	@DisplayName("the one-parameter version tests the created proof")
	void test_1() throws IllegalAccessException {
		constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE);
		verify(TestUtil.dependency(constructElGamalKeyShare, VerifyElGamalKeyShare.class))
				.apply(EL_GAMAL_KEY_SHARE_E);
	}

	@Test
	@DisplayName("if the generated proof does not verify (impossible) a CryptoError is thrown")
	void test_2() {
		assertThrows(CryptoError.class, () -> constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE_BAD));
	}
}
