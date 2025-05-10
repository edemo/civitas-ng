package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.common.UtilTestData;

public class SwallowTagTest {
	StringReader reader;
	StringReader readerWithAllWhiteSpace;
	StringReader readerWhitLeadingWhiteSpace;

	@BeforeEach
	private void setUp() {
		reader = new StringReader(UtilTestData.READER_CONTENT);
		readerWhitLeadingWhiteSpace = new StringReader(
				UtilTestData.ALL_WHITESPACE + UtilTestData.READER_CONTENT);
		readerWithAllWhiteSpace = new StringReader(UtilTestData.ALL_WHITESPACE);

	}

	@Test
	@DisplayName("swallowTag throws IllegalArgumentException for null tag")
	void test20() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowTag(reader, null));
	}

	@Test
	@DisplayName("swallowTag throws IllegalArgumentException for null reader")
	void test() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.swallowTag(null, UtilTestData.TAG_NAME));
	}

	@Test
	@DisplayName("swallowTag reads until end of tag")
	void test1() throws IllegalArgumentException, IOException {
		Util.swallowTag(reader, UtilTestData.TAG_NAME);
		assertEquals(UtilTestData.AFTER_TAG, TestUtil.readerToString(reader));
	}

	@Test
	@DisplayName("swallowTag skips over leading whitespace")
	void test2() throws IllegalArgumentException, IOException {
		Util.swallowTag(readerWhitLeadingWhiteSpace, UtilTestData.TAG_NAME);
		assertEquals(UtilTestData.AFTER_TAG,
				TestUtil.readerToString(readerWhitLeadingWhiteSpace));
	}

	@Test
	@DisplayName("swallowTag throws for all whitespace content")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowTag(readerWithAllWhiteSpace, UtilTestData.TAG_NAME));
	}

	@Test
	@DisplayName("swallowTag throws for different tag")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class, () -> Util
				.swallowTag(readerWithAllWhiteSpace, UtilTestData.FOUND_STRING));
	}

}
