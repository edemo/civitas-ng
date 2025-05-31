package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.util.Use;
import lombok.SneakyThrows;

public class ElGamalMsgCTest extends TestBase
		implements ElgamalMessageTestData {

	ElGamalMsg message;
	@Use
	EncodeMessage encodeMessage;

	@Override
	@BeforeEach
	@SneakyThrows()
	public void setUp() {
		super.setUp();
		message = new ElGamalMsg(
				encodeMessage.apply(BIGINT_A, EL_GAMAL_PARAMETERS));

	}

	@Test
	@DisplayName("Bigint only constructor just stores the message vithout verification")
	void test() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_A, new ElGamalMsg(BIGINT_A).m);
	}

	@Test
	@DisplayName("encodeMessage with int parameter stores the message converted to bigint and encrypted")
	void test1() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_G.modPow(SOME_INT_BIG, BIGINT_P),
				encodeMessage.apply(SOME_INT, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encodeMessage with String parameter stores the message encrypted")
	void test1_1() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_G.modPow(SOMESTRING_BIGINT, BIGINT_P),
				encodeMessage.apply(SOMESTRING, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("empty string cannot be used in encodeMessage")
	void test1_1_1() throws NumberFormatException, CryptoException {
		assertThrows(CryptoException.class,
				() -> encodeMessage.apply("", EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encodeMessage with BigInt parameter stores the message encrypted")
	void test1_2() throws NumberFormatException, CryptoException {
		assertEquals(BIGINT_G.modPow(BIGINT_A, BIGINT_P),
				encodeMessage.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("does not equal anything not ElGamalMsgC")
	void test11() throws NumberFormatException, CryptoException {
		assertFalse(message.equals(mock(ElGamalMsg.class)));
	}

}
