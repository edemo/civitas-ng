package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	@DisplayName("generates an int <n ")
	void test() {
		assertTrue(generateRandomInt.apply(SOME_SMALL_INT) < SOME_SMALL_INT);
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
