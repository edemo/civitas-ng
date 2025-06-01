package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.util.Tested;

public class DecodeChoiceTest extends TestBase
		implements ElGamalParametersTestData {

	@Tested
	DecodeChoice decodeChoice;

	@Test
	@DisplayName("decodes the original message based on the message value")
	void test() throws CryptoException {
		assertEquals(TWO_INT, decodeChoice.apply(DECODEMAP, TWO_ENCODED));
	}

	@Test
	@DisplayName("throws CryptoException if the message value is invalid")
	void test2() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> decodeChoice.apply(DECODEMAP, BIGINT_A));
	}

}
