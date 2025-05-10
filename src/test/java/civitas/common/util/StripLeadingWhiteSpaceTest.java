package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

class StripLeadingWhiteSpaceTest extends UtilTestBase {

	@DisplayName("stripLeadingWhitespace strips the leading whitespace")
	void test1() {
		assertEquals(VERSIONSTRING,
				Util.stripLeadingWhitespace(LEADING_WHITESPACE));
	}

	@Test
	@DisplayName("stripLeadingWhitespace returns null for null")
	void test2() {
		assertEquals(null, Util.stripLeadingWhitespace(null));
	}

	@Test
	@DisplayName("stripLeadingWhitespace return empty string for all whitespaces")
	void test3() {
		assertEquals("", Util.stripLeadingWhitespace(ALL_WHITESPACE));
	}

	@Test
	@DisplayName("stripLeadingWhitespace strips the leading whitespace")
	void test4() {
		assertEquals(VERSIONSTRING,
				Util.stripLeadingWhitespace(LEADING_WHITESPACE));
	}

	@Test
	@DisplayName("stripLeadingWhitespace returns the same if there is no leading whitespace")
	void test5() {
		assertEquals(TRAILING_WHITESPACE,
				Util.stripLeadingWhitespace(TRAILING_WHITESPACE));
	}

}
