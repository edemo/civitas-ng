package civitas.crypto.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoException;

public class SchnorrPrimeEncoderTest extends ConcreteTestBase
		implements EncoderTestData {

	SchnorrPrimeEncoder encoder = new SchnorrPrimeEncoder(EL_GAMAL_PARAMETERS);

	@Test
	@DisplayName("encode works as expected")
	void test() throws CryptoException {
		assertEquals(G_EXP_A_BASE64, Base64.getEncoder()
				.encodeToString(encoder.encodePlaintext(BIGINT_A).toByteArray()));
	}

	@Test
	@DisplayName("encode won't encode plaintext bigget than q")
	void test1() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> encoder.encodePlaintext(BIGINT_Q.add(BIGINT_A)).toString());
	}

	@Test
	@DisplayName("no decoding for Schnorr primes")
	void test2() throws CryptoException {
		assertThrows(CryptoException.class, () -> encoder.decodeMessage(BIGINT_A));
	}

}
