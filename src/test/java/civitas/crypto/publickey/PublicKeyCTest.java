package civitas.crypto.publickey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.privatekey.PrivateKey;
import civitas.crypto.privatekey.PrivateKeyC;
import civitas.util.Use;

public class PublicKeyCTest extends ConcreteTestBase
		implements PublicKeyTestData {

	@Use
	PublicKeyFromXML publicKeyFromXML;

	private static final PublicKeyC PUBLIC_KEY_C = new PublicKeyC(PUBLIC_KEY,
			PUBLIC_KEY_NAME);

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PUBLIC_KEY_XML, PUBLIC_KEY_C.toXML());
	}

	@Test
	@DisplayName("name returns the name of the key")
	void test1() {
		assertEquals(PUBLIC_KEY_NAME, PUBLIC_KEY_C.name());
	}

	@Test
	@DisplayName("isAuthorized checks if the private key is belonging to the public key")
	void test2() {
		assertTrue(PUBLIC_KEY_C.isAuthorized(new PrivateKeyC(PRIVATE_KEY), false));
	}

	@Test
	@DisplayName("isAuthorized is false for anything not a PrivateKeyC")
	void test2_1() {
		assertFalse(PUBLIC_KEY_C.isAuthorized(mock(PrivateKey.class), false));
	}

	@Test
	@DisplayName("isAuthorized is false for other private key")
	void test2_2() {
		assertFalse(
				PUBLIC_KEY_C.isAuthorized(new PrivateKeyC(PRIVATE_KEY2), false));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {
		PublicKeyC fromXML = (PublicKeyC) publicKeyFromXML
				.apply(new StringReader(PUBLIC_KEY_XML));
		assertEquals(PUBLIC_KEY_XML, fromXML.toXML());
	}

}
