package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

public class SafePrimeEncoderTest extends ConcreteTestBase {

	private SafePrimeEncoder encoder = new SafePrimeEncoder(
			EL_GAMAL_PARAMETERS_SAFE);

	@Test
	@DisplayName("encoder works as expected")
	void test() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(PUBLICIZED_SAFE_BIGINT_A_BASE64, Base64.getEncoder()
				.encodeToString(encoder.encodePlaintext(BIGINT_A).toByteArray()));
	}

	@Test
	@DisplayName("encoder works as expected when the plaintext's Legendre symbol is -1")
	void test2() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(PUBLICIZED_SAFE_LEGENDRE_BASE64,
				Base64.getEncoder().encodeToString(encoder
						.encodePlaintext(PLAINTEXT_WITH_LEGENDRE_MINUS_ONE).toByteArray()));
	}

	@Test
	@DisplayName("decoder works as expected")
	void test3() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(BIGINT_A, encoder.decodeMessage(new CivitasBigInteger(
				Base64.getDecoder().decode(PUBLICIZED_SAFE_BIGINT_A_BASE64))));
	}

	@Test
	@DisplayName("decoder works as expected for encoded text > q")
	void test3_1() throws CryptoException, IllegalArgumentException, IOException {
		assertEquals(PLAINTEXT_OF_BIG_SECRET_BASE64,
				Base64.getEncoder().encodeToString(
						encoder.decodeMessage(EL_GAMAL_PARAMETERS_SAFE.q.add(BIGINT_A))
								.toByteArray()));
	}

	@Test
	@DisplayName("decoder cannot decode messages bigger than p")
	void test4() throws CryptoException, IllegalArgumentException, IOException {
		assertThrows(CryptoException.class,
				() -> encoder.decodeMessage(EL_GAMAL_PARAMETERS_SAFE.p.add(BIGINT_A)));
	}

}
