package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class EscapeStringTest extends UtilTestBase {

	@Test
	@DisplayName("escapeString returns empty string for null")
	void test6_1() {
		assertEquals("", Util.escapeString(null));
	}

	@Test
	@DisplayName("escapeString(String,PrintWriter) silently does nothing if the printwriter is null")
	void test6_2() {
		assertDoesNotThrow(() -> Util.escapeString(UNESCAPED, null));
	}

	@Test
	@DisplayName("escapeString(String,PrintWriter) silently does nothing if the string is null")
	void test6_3() {
		Util.escapeString(null, printWriterMock);
		verifyNoInteractions(printWriterMock);
	}

	@Test
	@DisplayName("escapeString escapes & with &amp;")
	void test7() {
		assertEquals(ESCAPED_AMP, Util.escapeString(UNESCAPED_AMP));
	}

	@Test
	@DisplayName("escapeString escapes < with &lt;")
	void test8() {
		assertEquals(ESCAPED_LT, Util.escapeString(UNESCAPED_LT));
	}

	@Test
	@DisplayName("escapeString escapes > with &gt;")
	void test9() {
		assertEquals(ESCAPED_GT, Util.escapeString(UNESCAPED_GT));
	}

	@Test
	@DisplayName("escapeString escapes ' with &apos; FIXME: report bug in original code")
	void test10() {
		assertEquals(ESCAPED_APOS, Util.escapeString(UNESCAPED_APOS));
	}

	@Test
	@DisplayName("escapeString escapes \" with &quot;")
	void test11() {
		assertEquals(ESCAPED_QUOT, Util.escapeString(UNESCAPED_QUOT));
	}

	@Test
	@DisplayName("escapeString escapes multiple special characters")
	void test12() {
		assertEquals(ESCAPED, Util.escapeString(UNESCAPED));
	}

	@Test
	@DisplayName("unescapeString returns null for null")
	void test12_1() {
		assertEquals(null, Util.unescapeString(null));
	}

	@Test
	@DisplayName("escapeString(unescapeString()) is idempotent for correctly escaped string")
	void test15() {
		assertEquals(ESCAPED, Util.escapeString(Util.unescapeString(ESCAPED)));
	}

}
