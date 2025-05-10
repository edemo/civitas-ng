package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

public class IsNextTagTest extends UtilTestBase {

	@Test
	@DisplayName("isNextTag throws IllegalArgumentException for null tag")
	void test20() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.isNextTag(reader, null));
	}

	@Test
	@DisplayName("isNextTag throws IllegalArgumentException for null reader")
	void test() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.isNextTag(null, TAG));
	}

	@Test
	@DisplayName("isNextTag returns true if the next tag is the named one")
	void test1() throws IllegalArgumentException, IOException {
		assertTrue(Util.isNextTag(reader, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag does not eat the tag from the reader")
	void test1_1() throws IllegalArgumentException, IOException {
		Util.isNextTag(reader, TAG_NAME);
		assertEquals(READER_CONTENT, TestUtil.readerToString(reader));
	}

	@Test
	@DisplayName("isNextTag returns false if the next tag is the named one, but non-whitespace before it")
	void test2() throws IllegalArgumentException, IOException {
		assertFalse(Util.isNextTag(readerWithWordBeforeTag, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag eats the whitespace before the tag")
	void test3() throws IllegalArgumentException, IOException {
		assertTrue(Util.isNextTag(readerTagWhitLeadingWhiteSpace, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag tolerates the whitespace before the tag")
	void test4() throws IllegalArgumentException, IOException {
		Util.isNextTag(readerTagWhitLeadingWhiteSpace, TAG_NAME);
		assertEquals(READER_CONTENT,
				TestUtil.readerToString(readerTagWhitLeadingWhiteSpace));
	}

	@Test
	@DisplayName("isNextTag returns false for all whitespace")
	void test5() throws IllegalArgumentException, IOException {
		assertFalse(Util.isNextTag(readerWithAllWhiteSpace, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag returns false for unclosed tag")
	void test6() throws IllegalArgumentException, IOException {
		assertFalse(Util.isNextTag(readerTagNotEndingWell, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag returns false for stream ending mid-tag")
	void test7() throws IllegalArgumentException, IOException {
		assertFalse(Util.isNextTag(readerTagCut, TAG_NAME));
	}

	@Test
	@DisplayName("isNextTag returns false for a tag starting the same, but ending different")
	void test8() throws IllegalArgumentException, IOException {
		assertFalse(Util.isNextTag(readerTagDifferentEnd, TAG_NAME));
	}

}
