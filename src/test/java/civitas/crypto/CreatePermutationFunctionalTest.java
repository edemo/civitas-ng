package civitas.crypto;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;
import civitas.crypto.algorithms.CreatePermutation;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class CreatePermutationFunctionalTest extends TestBase
		implements PrivateKeyTestData, BasicValuesTestData {

	@Autowired
	CreatePermutation createPermutation;

	@Test
	@DisplayName("createPermutation creates a permutation of given size")
	void createPermutation() {

		int[] permutation = createPermutation.apply(SOME_SMALL_INT);
		boolean[] hasIt = new boolean[SOME_SMALL_INT];
		Arrays.fill(hasIt, false);
		for (int i = 0; i < SOME_SMALL_INT; i++) {
			hasIt[permutation[i]] = true;
		}
		for (int i = 0; i < SOME_SMALL_INT; i++) {
			if (!hasIt[i])
				fail();
		}
		assertTrue(true);
	}

}
