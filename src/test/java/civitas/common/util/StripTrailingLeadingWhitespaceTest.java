package civitas.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.common.UtilTestData;

public class StripTrailingLeadingWhitespaceTest {

	@Test
	@DisplayName("stripTrailingLeadingWhitespace returns null for null")
	void test4() {
		assertEquals(null, Util.stripTrailingLeadingWhitespace(null));
	}

	@Test
	@DisplayName("stripTrailingLeadingWhitespace returns empty string for all whitespaces")
	void test5() {
		assertEquals("", Util.stripTrailingLeadingWhitespace(UtilTestData.ALL_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingLeadingWhitespace returns the same if there is no leading or trailing whitespace")
	void test6() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.stripTrailingLeadingWhitespace(UtilTestData.VERSIONSTRING));
	}

	@Test
	@DisplayName("stripTrailingLeadingWhitespace strips the trailing whitespace")
	void test7() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.stripTrailingLeadingWhitespace(UtilTestData.TRAILING_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingLeadingWhitespace strips the leading whitespace")
	void test8() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.stripTrailingLeadingWhitespace(UtilTestData.LEADING_WHITESPACE));
	}

	@Test
	@DisplayName("stripTrailingLeadingWhitespace cuts leading and trailing whitespace")
	void test9() {
		assertEquals(UtilTestData.VERSIONSTRING,
				Util.stripTrailingLeadingWhitespace((UtilTestData.LEADING_TRAILING_WHITESPACE)));
	}

}
