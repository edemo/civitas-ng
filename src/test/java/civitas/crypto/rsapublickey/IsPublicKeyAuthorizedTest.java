package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoError;
import civitas.util.Use;

public class IsPublicKeyAuthorizedTest extends TestBase implements PublicKeyTestData {

	@Use
	IsPublicKeyAuthorized isPublicKeyAuthorized;

	@Test
	@DisplayName("isAuthorized checks if the private key is belonging to the public key")
	void test2() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		assertTrue(isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY));
	}

	@Test
	@DisplayName("isAuthorized is false for other private key")
	void test2_2() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		assertFalse(isPublicKeyAuthorized.apply(PUBLIC_KEY, PRIVATE_KEY2));
	}

}
