package civitas.crypto.parameters.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.VoteChoice;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.DecodeChoice;
import io.github.magwas.konveyor.testing.TestBase;

class DecodeChoiceTest extends TestBase implements ElGamalParametersTestData {

	@InjectMocks
	DecodeChoice decodeChoice;

	@Test
	@DisplayName("decodes the original message based on the message value")
	void test() throws CryptoException {
		assertEquals(VoteChoice.I_BEATS_J, decodeChoice.apply(DECODEMAP, I_BEATS_J_ENCODED));
	}

	@Test
	@DisplayName("throws CryptoException if the message value is invalid")
	void test2() {
		assertThrows(CryptoException.class, () -> decodeChoice.apply(DECODEMAP, BIGINT_A));
	}
}
