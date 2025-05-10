package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class BytesToStringTest extends UtilTestBase {
	@Test
	@DisplayName("stringToBytes returns the base64 decoded string as a byte array")
	void test() {
		assertEquals(BASE_64_ENCODED, Util.bytesToString(BASE_64_DECODED));
	}

	@Test
	@DisplayName("stringToBytes returns null for null")
	void test1() {
		assertEquals(null, Util.bytesToString(null));
	}

}
