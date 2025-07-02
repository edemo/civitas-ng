package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.Constants;

class GetRandomGeneratorTest extends TestBase implements Constants {

	@InjectMocks
	GetRandomGenerator getRandomGenerator;

	@Test
	@DisplayName("returns the random generator")
	void test() {
		assertEquals(RANDOM, getRandomGenerator.apply());
	}

}
