package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class ReadSimpleTagTest extends UtilTestBase {

	@Test
	@DisplayName("readSimpleTag throws IllegalArgumentException for null tag")
	void test20() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.readSimpleTag(readerEndTag, null));
	}

	@Test
	@DisplayName("readSimpleTag throws IllegalArgumentException for null readerEndTag")
	void test() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.readSimpleTag(null, TAG));
	}

	@Test
	@DisplayName("readSimpleTag returns what is in the tag")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(FOUND_STRING, Util.readSimpleTag(readerSimpleTag, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleTag returns empty string for empty tag (nothing in tag)")
	void test1_2() throws IllegalArgumentException, IOException {
		assertEquals("", Util.readSimpleTag(readerSimpleTagNothingIn, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleTag returns empty string for empty tag (ending with />)")
	void test1_1() throws IllegalArgumentException, IOException {
		assertEquals("", Util.readSimpleTag(readerSimpleTagEmpty, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleTag can handle whitespace between the tag name and closing '>'")
	void test2() throws IllegalArgumentException, IOException {

		assertEquals(FOUND_STRING,
				Util.readSimpleTag(readerSimpleTagWithAiryTag, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleTag throws IOException if closing '>' not found")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readSimpleTag(readerTagNotEndingWell, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleTag throws IOException if closing tag not found")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readSimpleTag(readerSimpleTagNoClosing, TAG_NAME));
	}

}
