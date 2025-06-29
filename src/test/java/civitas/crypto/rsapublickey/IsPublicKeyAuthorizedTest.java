package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.crypto.CryptoError;
import civitas.crypto.signature.SignatureTestData;

public class IsPublicKeyAuthorizedTest extends TestBase
		implements PublicKeyTestData, BasicValuesTestData, SignatureTestData {

	@InjectMocks
	IsPublicKeyAuthorized isPublicKeyAuthorized;

	@Test
	@DisplayName("isAuthorized checks if the private key is belonging to the public key\n"
			+ "- creates a new base64 nonce\n"
			+ "- signs the nonce with the private key"
			+ "- verifies that the signature is verifiable with the public key")
	void test2() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		boolean actual = isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY_JS);
		verify(isPublicKeyAuthorized.createFreshNonceBase64)
				.apply(AUTHENTICATION_NONCE_LENGTH);
		verify(isPublicKeyAuthorized.signWithPublicKey).apply(PRIVATE_KEY_JS,
				PUBLIC_KEY, AUTHENTICATION_NONCE);
		verify(isPublicKeyAuthorized.verifyPublicKeySignature).apply(
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE);
		assertTrue(actual);
	}

	@Test
	@DisplayName("isAuthorized is false for other private key")
	void test2_2() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		assertFalse(isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY_JS2));
	}

}
