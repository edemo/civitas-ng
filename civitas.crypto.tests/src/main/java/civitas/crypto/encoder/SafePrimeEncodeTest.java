package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SafePrimeEncode;
import io.github.magwas.testing.TestBase;

public class SafePrimeEncodeTest extends TestBase implements EncoderTestData {

	@InjectMocks
	private SafePrimeEncode encoder;

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

}
