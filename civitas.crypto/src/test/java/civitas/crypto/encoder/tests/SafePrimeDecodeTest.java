package civitas.crypto.encoder.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SafePrimeDecode;
import civitas.util.CivitasBigIntegerFactory;
import io.github.magwas.konveyor.testing.TestBase;

class SafePrimeDecodeTest extends TestBase implements EncoderTestData {

	@InjectMocks
	private SafePrimeDecode decoder;

	@Test
	@DisplayName("decoder works as expected")
	void test3() throws CryptoException {
		assertEquals(
				BIGINT_A,
				decoder.apply(
						CivitasBigIntegerFactory.obtain(Base64.getDecoder().decode(SAFE_P_MINUS_A_BASE64)),
						EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder works as expected for encoded text > q")
	void test3_1() throws CryptoException {
		assertEquals(
				SAFE_P.subtract(SAFE_Q).subtract(BIGINT_A),
				decoder.apply(SAFE_Q.add(BIGINT_A), EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder works as expected for encoded text = q")
	void test3_2() throws CryptoException {
		assertEquals(SAFE_Q, decoder.apply(SAFE_Q, EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder cannot decode messages bigger than p")
	void test4() {
		assertThrows(
				CryptoException.class,
				() -> decoder.apply(EL_GAMAL_PARAMETERS_SAFE.p.add(BIGINT_A), EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder returns zero if message = p")
	void test() throws CryptoException {
		assertEquals(ZERO, decoder.apply(SAFE_P, EL_GAMAL_PARAMETERS_SAFE));
	}
}
