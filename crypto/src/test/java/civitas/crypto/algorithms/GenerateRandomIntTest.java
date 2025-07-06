package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;

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
		assertTrue(actual < SOME_SMALL_INT, "actual:" + actual);
	}

	@Test
	@DisplayName("if n < 0, returns 0")
	void test2() {
		assertEquals(0, generateRandomInt.apply(-SOME_SMALL_INT));
	}

	@Test
	@DisplayName("if n == 0, returns 0")
	void test3() {
		assertEquals(0, generateRandomInt.apply(0));
	}

}
