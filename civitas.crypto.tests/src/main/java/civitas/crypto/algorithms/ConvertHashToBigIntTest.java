package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.util.BasicValuesTestData;
import io.github.magwas.testing.TestBase;

class ConvertHashToBigIntTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	ConvertHashToBigInt convertHashToBigInt;

	@Test
	@DisplayName("Converts a byte array to a positive CivitasBiginteger")
	void test() {
		assertEquals(BIGINT_A, convertHashToBigInt.apply(BIGINT_A.toByteArray()));
	}

}
