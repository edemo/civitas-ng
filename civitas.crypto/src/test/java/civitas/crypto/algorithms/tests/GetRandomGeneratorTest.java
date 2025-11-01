package civitas.crypto.algorithms.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import io.github.magwas.konveyor.testing.TestBase;

class GetRandomGeneratorTest extends TestBase implements Constants {

	@InjectMocks
	CryptoBase cryptoBase;

	@Test
	@DisplayName("returns the random generator")
	void test() {
		assertEquals(RANDOM, cryptoBase.getRandomGenerator());
	}
}
