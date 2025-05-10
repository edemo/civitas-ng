package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

public class SwallowTagTest extends UtilTestBase {

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
				() -> Util.swallowTag(null, TAG_NAME));
	}

	@Test
	@DisplayName("swallowTag reads until end of tag")
	void test1() throws IllegalArgumentException, IOException {
		Util.swallowTag(reader, TAG_NAME);
		assertEquals(AFTER_TAG, TestUtil.readerToString(reader));
	}

	@Test
	@DisplayName("swallowTag skips over leading whitespace")
	void test2() throws IllegalArgumentException, IOException {
		Util.swallowTag(readerWhitLeadingWhiteSpace, TAG_NAME);
		assertEquals(AFTER_TAG,
				TestUtil.readerToString(readerWhitLeadingWhiteSpace));
	}

	@Test
	@DisplayName("swallowTag throws for all whitespace content")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowTag(readerWithAllWhiteSpace, TAG_NAME));
	}

	@Test
	@DisplayName("swallowTag throws for different tag")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.swallowTag(readerWithAllWhiteSpace, FOUND_STRING));
	}

}
