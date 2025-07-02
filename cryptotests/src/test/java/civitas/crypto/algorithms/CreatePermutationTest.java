package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;

class CreatePermutationTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	CreatePermutation createPermutation;

	@Test
	void test() {
		assertArrayEquals(INTEGER_PERMUTATION, createPermutation.apply(5));
	}

}
