package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class StripTrailingWhitespaceTest extends UtilTestBase {

	@Test
	@DisplayName("stripTrailingWhitespace returns null for null")
	void test() {
		assertEquals(null, Util.stripTrailingWhitespace(null));
	}

	@Test
	@DisplayName("stripTrailingWhitespace returns empty string for all whitespaces")
	void test1() {
		assertEquals("", Util.stripTrailingWhitespace(ALL_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingWhitespace strips the trailing whitespace")
	void test2() {
		assertEquals(VERSIONSTRING,
				Util.stripTrailingWhitespace(TRAILING_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingWhitespace returns the same if there is no trailing whitespace")
	void test3() {
		assertEquals(LEADING_WHITESPACE,
				Util.stripTrailingWhitespace(LEADING_WHITESPACE));
	}

}
