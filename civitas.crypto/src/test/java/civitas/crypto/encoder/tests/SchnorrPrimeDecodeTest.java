package civitas.crypto.encoder.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SchnorrPrimeDecode;
import io.github.magwas.konveyor.testing.TestBase;

class SchnorrPrimeDecodeTest extends TestBase implements EncoderTestData {

	@InjectMocks
	SchnorrPrimeDecode schnorrPrimeDecoder;

	@Test
	@DisplayName("no decoding for Schnorr primes")
	void test2() {
		assertThrows(CryptoException.class, () -> schnorrPrimeDecoder.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}
}
