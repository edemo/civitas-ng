package civitas.functionaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.RandomAwareTestBase;
import civitas.crypto.algorithms.GenerateRandomInt;
import civitas.util.BasicValuesTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class GenerateRandomIntFunctionalTest extends RandomAwareTestBase
		implements BasicValuesTestData {
	@Autowired
	GenerateRandomInt generateRandomInt;

	@Test
	@DisplayName("generates an int <n ")
	void test() {
		for (int i = 0; i < RANDOM_RUNS; i++) {
			assertTrue(generateRandomInt.apply(SOME_SMALL_INT) < SOME_SMALL_INT);
		}
	}

	@Test
	@DisplayName("if n <= 0, returns 0")
	void test2() {
		for (int i = 0; i < RANDOM_RUNS; i++) {
			assertEquals(0, generateRandomInt.apply(-SOME_SMALL_INT));
		}
	}

}
