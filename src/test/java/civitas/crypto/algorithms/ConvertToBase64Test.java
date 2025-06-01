package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.util.Tested;

public class ConvertToBase64Test extends TestBase
		implements BasicValuesTestData {

	@Tested
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
