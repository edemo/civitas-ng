package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.util.BasicValuesTestData;
import io.github.magwas.testing.TestBase;

class ConvertToBase64Test extends TestBase
		implements BasicValuesTestData {

	@InjectMocks
	ConvertToBase64 convertToBase64;

	@Test
	@DisplayName("converts a CivitasBigInteger to base64")
	void test() {
		assertEquals(BIGINT_A_BASE64, convertToBase64.apply(BIGINT_A));
	}

	@Test
	@DisplayName("converts a byte array to base64")
	void test2() {
		assertEquals(SOMESTRING_BASE64,
				convertToBase64.apply(SOMESTRING.getBytes()));
	}

}
