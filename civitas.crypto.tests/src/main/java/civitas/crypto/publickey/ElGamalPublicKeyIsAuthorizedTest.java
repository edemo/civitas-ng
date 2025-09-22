package civitas.crypto.publickey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.privatekey.ElGamalPrivateKey;
import io.github.magwas.testing.TestBase;

class ElGamalPublicKeyIsAuthorizedTest extends TestBase
		implements ElGamalPublicKeyTestData {

	@InjectMocks
	ElGamalPublicKeyisAuthorized elGamalPublicKeyisAuthorized;

	@Test
	@DisplayName("isAuthorized checks if the proof is the private key for this")
	void test3() throws IOException {
		assertTrue(elGamalPublicKeyisAuthorized.apply(EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PRIVATE_KEY_E));
	}

	@Test
	@DisplayName("isAuthorized is false for a secret which is not a private key")
	void test4() throws IOException {
		assertFalse(
				elGamalPublicKeyisAuthorized.apply(EL_GAMAL_PUBLIC_KEY_E, BIGINT_A));
	}

	@Test
	@DisplayName("isAuthorized is false for bad secret")
	void test5() throws IOException {
		assertFalse(elGamalPublicKeyisAuthorized.apply(EL_GAMAL_PUBLIC_KEY_E,
				new ElGamalPrivateKey(BIGINT_B, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("isAuthorized throws NullPointerException for a private key with null")
	void test6() throws IOException {
		assertThrows(NullPointerException.class,
				() -> elGamalPublicKeyisAuthorized.apply(EL_GAMAL_PUBLIC_KEY_E,
						new ElGamalPrivateKey(null, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("not equals to other key with different y but same parameters "
			+ "FIXME original code did not test for y")
	void equalsTest2() throws IOException {
        assertNotEquals(EL_GAMAL_PUBLIC_KEY_EPRIME, new ElGamalPublicKey(BIGINT_B, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("not equals to anything not ElGamalPublicKeyC")
	void equalsTest3() throws IOException {
        assertNotEquals(EL_GAMAL_PUBLIC_KEY_EPRIME, mock(ElGamalPublicKey.class));
	}

}
