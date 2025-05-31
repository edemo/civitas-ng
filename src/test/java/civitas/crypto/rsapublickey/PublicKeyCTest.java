package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoError;
import civitas.util.Use;

public class PublicKeyCTest extends ConcreteTestBase
		implements PublicKeyTestData {

	@Use
	PublicKeyFromXML publicKeyFromXML;

	@Use
	IsPublicKeyAuthorized isPublicKeyAuthorized;

	@Use
	PublicKeyCToXML publicKeyCToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PUBLIC_KEY_XML, publicKeyCToXML.apply(PUBLIC_KEY));
	}

	@Test
	@DisplayName("name returns the name of the key")
	void test1() {
		assertEquals(PUBLIC_KEY_NAME, PUBLIC_KEY.name);
	}

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

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {
		PublicKey fromXML = publicKeyFromXML
				.apply(new StringReader(PUBLIC_KEY_XML));
		assertEquals(PUBLIC_KEY, fromXML);
	}

}
