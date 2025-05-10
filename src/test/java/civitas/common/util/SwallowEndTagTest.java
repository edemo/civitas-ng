package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.common.UtilTestData;

public class SwallowEndTagTest extends UtilTestBase {

	@Test
	@DisplayName("swallowEndTag throws IllegalArgumentException for null tag")
	void test20() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowEndTag(readerEndTag, null));
	}

	@Test
	@DisplayName("swallowEndTag throws IllegalArgumentException for null readerEndTag")
	void test() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowEndTag(null, "div"));
	}

	@Test
	@DisplayName("swallowEndTag reads until end of tag")
	void test1() throws IllegalArgumentException, IOException {
		Util.swallowEndTag(readerEndTag, "div");
		assertEquals(UtilTestData.AFTER_TAG, TestUtil.readerToString(readerEndTag));
	}

	@Test
	@DisplayName("swallowEndTag skips over leading whitespace")
	void test2() throws IllegalArgumentException, IOException {
		Util.swallowEndTag(readerEndTagWhitLeadingWhiteSpace, "div");
		assertEquals(UtilTestData.AFTER_TAG,
				TestUtil.readerToString(readerEndTagWhitLeadingWhiteSpace));
	}

	@Test
	@DisplayName("swallowEndTag throws for all whitespace content")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowEndTag(readerEndTagWithAllWhiteSpace, "div"));
	}

	@Test
	@DisplayName("swallowEndTag throws for different tag")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowEndTag(readerEndTagWithAllWhiteSpace, "li"));
	}

}
