package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.util.BasicValuesTestData;
import io.github.magwas.testing.TestBase;

class CreatePermutationTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	CreatePermutation createPermutation;

	@Test
	void test() {
		assertArrayEquals(INTEGER_PERMUTATION, createPermutation.apply(5));
	}
}
