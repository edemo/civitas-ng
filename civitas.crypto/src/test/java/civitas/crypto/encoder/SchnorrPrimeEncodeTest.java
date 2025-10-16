package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncode;
import io.github.magwas.testing.TestBase;

class SchnorrPrimeEncodeTest extends TestBase implements EncoderTestData {

	@InjectMocks
	SchnorrPrimeEncode schnorrPrimeEncoder;

	@Test
	@DisplayName("encode works as expected")
	void test() throws CryptoException {
		assertEquals(G_EXP_A, schnorrPrimeEncoder.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encode won't encode plaintext bigger than q")
	void test1() {
		assertThrows(CryptoException.class, () -> schnorrPrimeEncoder
				.apply(BIGINT_Q.add(BIGINT_A), EL_GAMAL_PARAMETERS)
				.toString());
	}

	@Test
	@DisplayName("encode q to g^q")
	void test2() throws CryptoException {
		assertEquals(BIGINT_G.modPow(BIGINT_Q, BIGINT_P), schnorrPrimeEncoder.apply(BIGINT_Q, EL_GAMAL_PARAMETERS));
	}
}
