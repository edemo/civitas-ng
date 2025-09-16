package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SchnorrPrimeDecode;
import io.github.magwas.testing.TestBase;

public class SchnorrPrimeDecodeTest extends TestBase
		implements EncoderTestData {

	@InjectMocks
	SchnorrPrimeDecode schnorrPrimeDecoder;

	@Test
	@DisplayName("no decoding for Schnorr primes")
	void test2() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> schnorrPrimeDecoder.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

}
