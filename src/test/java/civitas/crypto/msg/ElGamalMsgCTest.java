package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoException;

public class ElGamalMsgCTest extends ConcreteTestBase
		implements ElgamalMessageTestData {

	ElGamalMsgC message;

	@Override
	@BeforeEach
	public void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		super.setUp();
		message = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS_SAFE);

	}

	@Test
	@DisplayName("Bigint only constructor just stores the message vithout verification")
	void test() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_A, new ElGamalMsgC(BIGINT_A).m);
	}

	@Test
	@DisplayName("constructor with int parameter stores the message converted to bigint and encrypted")
	void test1() throws NumberFormatException, CryptoException {
		assertEquals(SOME_INT_BIG,
				new ElGamalMsgC(SOME_INT, EL_GAMAL_PARAMETERS_SAFE).m);
	}

	@Test
	@DisplayName("constructor with String parameter stores the message encrypted")
	void test1_1() throws NumberFormatException, CryptoException {
		assertEquals(SAFE_P_MINUS_A,
				new ElGamalMsgC(SOMESTRING, EL_GAMAL_PARAMETERS_SAFE).m);
	}

	@Test
	@DisplayName("empty string cannot be used in constructor")
	void test1_1_1() throws NumberFormatException, CryptoException {
		assertThrows(CryptoException.class,
				() -> new ElGamalMsgC("", EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("constructor with BigInt parameter stores the message encrypted")
	void test1_2() throws NumberFormatException, CryptoException {
		assertEquals(SAFE_P_MINUS_A,
				new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS_SAFE).m);
	}

	@Test
	@DisplayName("plaintextBigIntValue can be retrieved using a safe prime group")
	void test6() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_A,
				message.plaintextBigIntValue(EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("bigIntValue just retrieves the message")
	void test8() throws NumberFormatException, CryptoException {
		assertEquals(message.m, message.bigIntValue());
	}

	@Test
	@DisplayName("plaintextIntValue returns the int value of the message")
	void test7() throws NumberFormatException, CryptoException {
		assertEquals(SOME_INT, new ElGamalMsgC(SOME_INT_BIG)
				.plaintextIntValue(EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("plaintextStringValue returns the plain text as string")
	void test9() throws NumberFormatException, CryptoException {
		assertEquals(SOMESTRING,
				message.plaintextStringValue(EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("toString returns the message as base64 encoded string")
	void test10() throws NumberFormatException, CryptoException {
		assertEquals(SOMESTRING_BASE64, new ElGamalMsgC(BIGINT_A).toString());
	}

	@Test
	@DisplayName("does not equal anything not ElGamalMsgC")
	void test11() throws NumberFormatException, CryptoException {
		assertFalse(message.equals(mock(ElGamalMsg.class)));
	}

	@Test
	@DisplayName("hashCode is the hashCode of the message")
	void test12() throws NumberFormatException, CryptoException {
		assertEquals(message.m.hashCode(), message.hashCode());
	}

}
