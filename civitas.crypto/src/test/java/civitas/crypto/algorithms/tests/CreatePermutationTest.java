package civitas.crypto.algorithms.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.algorithms.CreatePermutation;
import civitas.util.tests.BasicValuesTestData;
import io.github.magwas.konveyor.testing.TestBase;

class CreatePermutationTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	CreatePermutation createPermutation;

	@Test
	void test() {
		assertArrayEquals(INTEGER_PERMUTATION, createPermutation.apply(5));
	}
}
