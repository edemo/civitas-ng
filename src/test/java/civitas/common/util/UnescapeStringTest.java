package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.common.UtilTestData;

public class UnescapeStringTest {
	@Test
	@DisplayName("unescapeString is the inverse of escapeString")
	void test13() {
		assertEquals(UtilTestData.UNESCAPED, Util.unescapeString(UtilTestData.ESCAPED));
	}

	@Test
	@DisplayName("unescapeString correctly handles the part after the last escape sequence")
	void test13_2() {
		assertEquals(UtilTestData.UNESCAPED + "a", Util.unescapeString(UtilTestData.ESCAPED + "a"));
	}

	@Test
	@DisplayName("unescapeString correctly handles the part after the last semicolon")
	void test13_3() {
		assertEquals(UtilTestData.UNESCAPED + "&a", Util.unescapeString(UtilTestData.ESCAPED + "&a"));
	}

	@Test
	@DisplayName("unescapeString leaves unknown escape sequences as is")
	void test13_4() {
		assertEquals(UtilTestData.UNESCAPED + "&ridiculous;a",
				Util.unescapeString(UtilTestData.ESCAPED + "&ridiculous;a"));
	}

	@Test
	@DisplayName("unescapeString returns the string if there is no & in it")
	void test13_1() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.unescapeString(UtilTestData.VERSIONSTRING));
	}

	@Test
	@DisplayName("unescapeString(escapeString()) is idempotent")
	void test14() {
		assertEquals(UtilTestData.UNESCAPED, Util.unescapeString(Util.escapeString(UtilTestData.UNESCAPED)));
	}

}
