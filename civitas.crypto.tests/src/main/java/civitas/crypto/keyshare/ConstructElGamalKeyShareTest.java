package civitas.crypto.keyshare;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoError;
import civitas.crypto.keypairshare.ElGamalKeyPairShareTestData;
import civitas.crypto.keys.ElGamalKeyShareTestData;
import io.github.magwas.testing.TestBase;

public class ConstructElGamalKeyShareTest extends TestBase
		implements ElGamalKeyPairShareTestData, ElGamalKeyShareTestData {

	@InjectMocks
	ConstructElGamalKeyShare constructElGamalKeyShare;

	@Test
	@DisplayName("the one-parameter version returns the public key with the proof of knowledge for the private key")
	void test() {
		ElGamalKeyShare actual = constructElGamalKeyShare
				.apply(EL_GAMAL_KEYPAIR_SHARE);
		assertTrue(EL_GAMAL_KEY_SHARE_E.equals(actual));
	}

	@Test
	@DisplayName("the one-parameter version tests the created proof")
	void test_1() {
		constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE);
		verify(constructElGamalKeyShare.verifyElGamalKeyShare)
				.apply(EL_GAMAL_KEY_SHARE_E);
	}

	@Test
	@DisplayName("if the generated proof does not verify (impossible) a CryptoError is thrown")
	void test_2() {
		assertThrows(CryptoError.class,
				() -> constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE_BAD));
	}

}
