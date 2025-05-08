package civitas.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.common.UtilTestData;

public class StripTrailingWhitespaceTest {

	@Test
	@DisplayName("stripTrailingWhitespace returns null for null")
	void test() {
		assertEquals(null, Util.stripTrailingWhitespace(null));
	}

	@Test
	@DisplayName("stripTrailingWhitespace returns empty string for all whitespaces")
	void test1() {
		assertEquals("", Util.stripTrailingWhitespace(UtilTestData.ALL_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingWhitespace strips the trailing whitespace")
	void test2() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.stripTrailingWhitespace(UtilTestData.TRAILING_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingWhitespace returns the same if there is no trailing whitespace")
	void test3() {
		assertEquals(UtilTestData.LEADING_WHITESPACE, Util.stripTrailingWhitespace(UtilTestData.LEADING_WHITESPACE));
	}

}
