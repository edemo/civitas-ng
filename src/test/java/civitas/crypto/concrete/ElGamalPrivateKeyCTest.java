package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamalPrivateKey;
import civitas.crypto.importing.ElGamalPrivateKeyFromXML;
import civitas.util.Use;

public class ElGamalPrivateKeyCTest extends ConcreteTestBase
		implements ElGamalPrivateKeyCTestData {
	@Use
	ElGamalPrivateKeyFromXML elGamalPrivateKeyFromXML;

	@Test
	@DisplayName("constructor with key and params works")
	void test0() {
		assertEquals(ELGAMAL_PRIVATE_KEY,
				new ElGamalPrivateKeyC(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("toXML skips null key and params")
	void test00() {
		assertEquals(EL_GAMAL_PRIVATE_KEY_NULL_XML,
				new ElGamalPrivateKeyC(null, null).toXML());
	}

	@Test
	@DisplayName("Equals takes x into account"
			+ "FIXME: equals do not take x into account in original code (no override)")
	void test0_0() {
		assertNotEquals(ELGAMAL_PRIVATE_KEY,
				new ElGamalPrivateKeyC(BIGINT_C, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("Equals takes parameters into account")
	void test0_1() {
		assertNotEquals(ELGAMAL_PRIVATE_KEY,
				new ElGamalPrivateKeyC(BIGINT_D, EL_GAMAL_PARAMETERS_OTHER));
	}

	@Test
	@DisplayName("toXML returns an XML representation")
	void test() {
		assertEquals(ELGAMAL_PRIVATE_KEY_XML, ELGAMAL_PRIVATE_KEY.toXML());
	}

	@Test
	@DisplayName("fromXML returns the private key")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(ELGAMAL_PRIVATE_KEY, elGamalPrivateKeyFromXML
				.apply(new StringReader(ELGAMAL_PRIVATE_KEY_XML)));
	}

	@Test
	@DisplayName("getParams gets the parameters")
	void test2() {
		assertEquals(EL_GAMAL_PARAMETERS, ELGAMAL_PRIVATE_KEY.getParams());
	}

	@Test
	@DisplayName("does not equal anything which is not ElGamalPrivateKeyC")
	void test3() {
		assertFalse(ELGAMAL_PRIVATE_KEY.equals(mock(ElGamalPrivateKey.class)));
	}

}
