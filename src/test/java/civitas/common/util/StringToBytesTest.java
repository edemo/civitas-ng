package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class StringToBytesTest extends UtilTestBase {

	@Test
	@DisplayName("stringToBytes returns the base64 decoded string as a byte array")
	void test() {
		assertArrayEquals(BASE_64_DECODED, Util.stringToBytes(BASE_64_ENCODED));
	}

	@Test
	@DisplayName("stringToBytes returns null for null")
	void test1() {
		assertEquals(null, Util.stringToBytes(null));
	}
}
