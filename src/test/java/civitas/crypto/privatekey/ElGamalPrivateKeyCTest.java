package civitas.crypto.privatekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalPrivateKeyCTest extends TestBase
		implements ElGamalPrivateKeyCTestData {
	@Use
	ElGamalPrivateKeyFromXML elGamalPrivateKeyFromXML;

	@Test
	@DisplayName("constructor with key and params works")
	void test0() {
		assertEquals(ELGAMAL_PRIVATE_KEY_E,
				new ElGamalPrivateKey(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

	@Use
	ElGamalPrivateKeyToXML elGamalPrivateKeyToXML;

	@Test
	@DisplayName("Equals takes x into account"
			+ "FIXME: equals do not take x into account in original code (no override)")
	void test0_0() {
		assertNotEquals(ELGAMAL_PRIVATE_KEY_E,
				new ElGamalPrivateKey(BIGINT_C, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("Equals takes parameters into account")
	void test0_1() {
		assertNotEquals(ELGAMAL_PRIVATE_KEY_E,
				new ElGamalPrivateKey(BIGINT_D, EL_GAMAL_PARAMETERS_OTHER));
	}

	@Test
	@DisplayName("toXML returns an XML representation")
	void test() {
		assertEquals(ELGAMAL_PRIVATE_KEY_XML,
				elGamalPrivateKeyToXML.apply(ELGAMAL_PRIVATE_KEY_E));
	}

	@Test
	@DisplayName("fromXML returns the private key")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(ELGAMAL_PRIVATE_KEY_E, elGamalPrivateKeyFromXML
				.apply(new StringReader(ELGAMAL_PRIVATE_KEY_XML)));
	}

	@Test
	@DisplayName("getParams gets the parameters")
	void test2() {
		assertEquals(EL_GAMAL_PARAMETERS, ELGAMAL_PRIVATE_KEY_E.params);
	}

	@Test
	@DisplayName("does not equal anything which is not ElGamalPrivateKeyC")
	void test3() {
		assertFalse(ELGAMAL_PRIVATE_KEY_E.equals(mock(ElGamalPrivateKey.class)));
	}

}
