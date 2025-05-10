package civitas.common.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class InvertPermutationTest extends UtilTestBase {

	@Test
	@DisplayName("invertPermutation inverts the permutation if it was correct")
	void test6() {

		assertArrayEquals(INVERT_PERMUTED_ARRAY,
				Util.invertPermutation(PERMUTED_ARRAY));
	}

	@Test
	@DisplayName("invertPermutation returns null for null")
	void test() {
		assertEquals(null, Util.invertPermutation(null));
	}

	@Test
	@DisplayName("invertPermutation silently swallows out of bond indexes,"
			+ "leaving default (0) in missing positions FIXME: potential bug")
	void test2() {
		assertArrayEquals(INVERT_PERMUTED_ARRAY_OOB,
				Util.invertPermutation(PERMUTED_ARRAY_OUT_OF_BONDS));
	}

	@Test
	@DisplayName("invertPermutation does not check for duplicate indexes"
			+ "FIXME: potential bug")
	void test3() {
		assertArrayEquals(INVERT_PERMUTED_ARRAY_W_DUP,
				Util.invertPermutation(PERMUTED_ARRAY_W_DUPLICATE));
	}

}
