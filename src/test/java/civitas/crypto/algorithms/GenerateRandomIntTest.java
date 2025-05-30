package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.ConcreteTestBase;
import civitas.util.Tested;

public class GenerateRandomIntTest extends ConcreteTestBase
		implements BasicValuesTestData {
	@Tested
	GenerateRandomInt generateRandomInt;

	@Test
	@Tag("functional")
	@DisplayName("generates an int <n ")
	void test() {
		for (int i = 0; i < RANDOM_RUNS; i++)
			assertTrue(generateRandomInt.apply(SOME_SMALL_INT) < SOME_SMALL_INT);
	}

	@Test
	@Tag("functional")
	@DisplayName("if n <= 0, returns 0")
	void test2() {
		for (int i = 0; i < RANDOM_RUNS; i++)
			assertEquals(0, generateRandomInt.apply(-SOME_SMALL_INT));
	}

}
