package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.signature.SignatureTestData;
import civitas.util.BasicValuesTestData;
import io.github.magwas.testing.TestBase;

class IsPublicKeyAuthorizedTest extends TestBase
		implements PublicKeyTestData, BasicValuesTestData, SignatureTestData,
		PrivateKeyTestData {

	@InjectMocks
	IsPublicKeyAuthorized isPublicKeyAuthorized;

	@Test
	@DisplayName("""
			isAuthorized checks if the private key is belonging to the public key
			- creates a new base64 nonce
			- signs the nonce with the private key
			- verifies that the signature is verifiable with the public key
			""")
	void test2() throws CryptoException {
		boolean actual = isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY);
		verify(isPublicKeyAuthorized.createFreshNonceBase64)
				.apply(AUTHENTICATION_NONCE_LENGTH);
		verify(isPublicKeyAuthorized.signWithPublicKey).apply(PRIVATE_KEY,
				PUBLIC_KEY, AUTHENTICATION_NONCE);
		verify(isPublicKeyAuthorized.verifyPublicKeySignature).apply(
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE);
		assertTrue(actual);
	}

	@Test
	@DisplayName("isAuthorized is false for other private key")
	void test2_2() throws CryptoException {
		assertFalse(isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY2));
	}

}
