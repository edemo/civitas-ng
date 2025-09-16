package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.util.BasicValuesTestData;
import io.github.magwas.testing.TestBase;

public class GenerateRandomIntTest extends TestBase
		implements BasicValuesTestData {
	@InjectMocks
	GenerateRandomInt generateRandomInt;

	@Test
	@DisplayName("uses the right random number generator")
	void test4() {
		int actual = generateRandomInt.apply(SOME_SMALL_INT);
		verify(generateRandomInt.cryptoBase.getRandomGenerator())
				.nextInt(SOME_SMALL_INT);
		assertEquals(SOME_SMALL_INT - 1, actual, "actual:" + actual);
	}

	@Test
	@DisplayName("if n < 0, returns 0")
	void test2() {
		int actual = generateRandomInt.apply(-1);
		verifyNoInteractions(generateRandomInt.cryptoBase.getRandomGenerator());
		assertEquals(0, actual);
	}

	@Test
	@DisplayName("if n == 0, returns 0")
	void test3() {
		int actual = generateRandomInt.apply(0);
		verifyNoInteractions(generateRandomInt.cryptoBase.getRandomGenerator());
		assertEquals(0, actual);
	}

}
