package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

public class SwallowEndTagTest extends UtilTestBase {

	@Test
	@DisplayName("swallowEndTag throws IllegalArgumentException for null tag")
	void test20() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowEndTag(readerEndTag, null));
	}

	@Test
	@DisplayName("swallowEndTag throws IllegalArgumentException for null reader")
	void test() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowEndTag(null, TAG_NAME));
	}

	@Test
	@DisplayName("swallowEndTag reads until end of tag")
	void test1() throws IllegalArgumentException, IOException {
		Util.swallowEndTag(readerEndTag, TAG_NAME);
		assertEquals(AFTER_TAG, TestUtil.readerToString(readerEndTag));
	}

	@Test
	@DisplayName("swallowEndTag skips over leading whitespace")
	void test2() throws IllegalArgumentException, IOException {
		Util.swallowEndTag(readerEndTagWhitLeadingWhiteSpace, "div");
		assertEquals(AFTER_TAG,
				TestUtil.readerToString(readerEndTagWhitLeadingWhiteSpace));
	}

	@Test
	@DisplayName("swallowEndTag throws for all whitespace content")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowEndTag(readerWithAllWhiteSpace, TAG));
	}

	@Test
	@DisplayName("swallowEndTag throws for different tag")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class, () -> Util
				.swallowEndTag(readerEndTagWhitLeadingWhiteSpace, FOUND_STRING));
	}

}
