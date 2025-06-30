package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SafePrimeDecode;
import civitas.crypto.parameters.encoder.SafePrimeEncode;
import civitas.util.CivitasBigInteger;

public class SafePrimeEncoderTest extends TestBase implements EncoderTestData {

	@InjectMocks
	private SafePrimeEncode encoder;
	@InjectMocks
	private SafePrimeDecode decoder;

	@Test
	@DisplayName("encoder works as expected")
	void test() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(SAFE_P_MINUS_A_BASE64, Base64.getEncoder().encodeToString(
				encoder.apply(BIGINT_A, EL_GAMAL_PARAMETERS_SAFE).toByteArray()));
	}

	@Test
	@DisplayName("encoder works as expected when the plaintext's Legendre symbol is -1")
	void test2() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(PUBLICIZED_SAFE_LEGENDRE, encoder
				.apply(PLAINTEXT_WITH_LEGENDRE_MINUS_ONE, EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder works as expected")
	void test3() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(BIGINT_A,
				decoder.apply(
						new CivitasBigInteger(
								Base64.getDecoder().decode(SAFE_P_MINUS_A_BASE64)),
						EL_GAMAL_PARAMETERS_SAFE));
	}

	@Test
	@DisplayName("decoder works as expected for encoded text > q")
	void test3_1() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(PLAINTEXT_OF_BIG_SECRET_BASE64,
				Base64.getEncoder().encodeToString(
						decoder.apply(EL_GAMAL_PARAMETERS_SAFE.q.add(BIGINT_A),
								EL_GAMAL_PARAMETERS_SAFE).toByteArray()));
	}

	@Test
	@DisplayName("decoder cannot decode messages bigger than p")
	void test4() throws CryptoException, IllegalArgumentException, IOException {
		assertThrows(CryptoException.class,
				() -> decoder.apply(EL_GAMAL_PARAMETERS_SAFE.p.add(BIGINT_A),
						EL_GAMAL_PARAMETERS_SAFE));
	}

}
