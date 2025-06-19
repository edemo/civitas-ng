package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SchnorrPrimeDecode;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncode;

public class SchnorrPrimeEncoderTest extends TestBase
		implements EncoderTestData {

	@InjectMocks
	SchnorrPrimeEncode schnorrPrimeEncoder;

	@InjectMocks
	SchnorrPrimeDecode schnorrPrimeDecoder;

	@Test
	@DisplayName("encode works as expected")
	void test() throws CryptoException {
		assertEquals(G_EXP_A_BASE64,
				Base64.getEncoder().encodeToString(schnorrPrimeEncoder
						.apply(BIGINT_A, EL_GAMAL_PARAMETERS).toByteArray()));
	}

	@Test
	@DisplayName("encode won't encode plaintext bigget than q")
	void test1() throws CryptoException {
		assertThrows(CryptoException.class, () -> schnorrPrimeEncoder
				.apply(BIGINT_Q.add(BIGINT_A), EL_GAMAL_PARAMETERS).toString());
	}

	@Test
	@DisplayName("no decoding for Schnorr primes")
	void test2() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> schnorrPrimeDecoder.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

}
