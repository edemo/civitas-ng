package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.PrintWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.common.UtilTestData;

public class EscapeStringTest {

	@Test
	@DisplayName("escapeString returns empty string for null")
	void test6_1() {
		assertEquals("", Util.escapeString(null));
	}

	@Test
	@DisplayName("escapeString(String,PrintWriter) silently does nothing if the printwriter is null")
	void test6_2() {
		assertDoesNotThrow(() -> Util.escapeString(UtilTestData.UNESCAPED, null));
	}

	@Test
	@DisplayName("escapeString(String,PrintWriter) silently does nothing if the string is null")
	void test6_3() {
		PrintWriter pw = mock(PrintWriter.class);
		Util.escapeString(null, pw);
		verifyNoInteractions(pw);
	}

	@Test
	@DisplayName("escapeString escapes & with &amp;")
	void test7() {
		assertEquals(UtilTestData.ESCAPED_AMP,
				Util.escapeString(UtilTestData.UNESCAPED_AMP));
	}

	@Test
	@DisplayName("escapeString escapes < with &lt;")
	void test8() {
		assertEquals(UtilTestData.ESCAPED_LT,
				Util.escapeString(UtilTestData.UNESCAPED_LT));
	}

	@Test
	@DisplayName("escapeString escapes > with &gt;")
	void test9() {
		assertEquals(UtilTestData.ESCAPED_GT,
				Util.escapeString(UtilTestData.UNESCAPED_GT));
	}

	@Test
	@DisplayName("escapeString escapes ' with &apos; FIXME: report bug in original code")
	void test10() {
		assertEquals(UtilTestData.ESCAPED_APOS,
				Util.escapeString(UtilTestData.UNESCAPED_APOS));
	}

	@Test
	@DisplayName("escapeString escapes \" with &quot;")
	void test11() {
		assertEquals(UtilTestData.ESCAPED_QUOT,
				Util.escapeString(UtilTestData.UNESCAPED_QUOT));
	}

	@Test
	@DisplayName("escapeString escapes multiple special characters")
	void test12() {
		assertEquals(UtilTestData.ESCAPED,
				Util.escapeString(UtilTestData.UNESCAPED));
	}

	@Test
	@DisplayName("unescapeString returns null for null")
	void test12_1() {
		assertEquals(null, Util.unescapeString(null));
	}

	@Test
	@DisplayName("escapeString(unescapeString()) is idempotent for correctly escaped string")
	void test15() {
		assertEquals(UtilTestData.ESCAPED,
				Util.escapeString(Util.unescapeString(UtilTestData.ESCAPED)));
	}

}
