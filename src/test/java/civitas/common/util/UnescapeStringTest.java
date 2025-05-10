package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class UnescapeStringTest extends UtilTestBase {

	@Test
	@DisplayName("unescapeString is the inverse of escapeString")
	void test13() {
		assertEquals(UNESCAPED, Util.unescapeString(ESCAPED));
	}

	@Test
	@DisplayName("unescapeString correctly handles the part after the last escape sequence")
	void test13_2() {
		assertEquals(UNESCAPED + AFTER_TAG,
				Util.unescapeString(ESCAPED + AFTER_TAG));
	}

	@Test
	@DisplayName("unescapeString correctly handles the part after the last semicolon")
	void test13_3() {
		assertEquals(UNESCAPED + AMPERSAND + AFTER_TAG,
				Util.unescapeString(ESCAPED + AMPERSAND + AFTER_TAG));
	}

	@Test
	@DisplayName("unescapeString leaves unknown escape sequences as is")
	void test13_4() {
		assertEquals(UNESCAPED + UNKNOWN_SEQUENCE,
				Util.unescapeString(ESCAPED + UNKNOWN_SEQUENCE));
	}

	@Test
	@DisplayName("unescapeString returns the string if there is no & in it")
	void test13_1() {
		assertEquals(VERSIONSTRING, Util.unescapeString(VERSIONSTRING));
	}

	@Test
	@DisplayName("unescapeString(escapeString()) is idempotent")
	void test14() {
		assertEquals(UNESCAPED, Util.unescapeString(Util.escapeString(UNESCAPED)));
	}

}
