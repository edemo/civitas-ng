package civitas.crypto.algorithms.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.util.tests.BasicValuesTestData;
import io.github.magwas.konveyor.testing.TestBase;

class ConvertHashToBigIntTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	ConvertHashToBigInt convertHashToBigInt;

	@Test
	@DisplayName("Converts a byte array to a positive CivitasBiginteger")
	void test() {
		assertEquals(BIGINT_A, convertHashToBigInt.apply(BIGINT_A.toByteArray()));
	}
}
