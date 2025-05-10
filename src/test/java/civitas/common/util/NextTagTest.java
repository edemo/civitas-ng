package civitas.common.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.common.UtilTestData;

class NextTagTest extends UtilTestBase {

	@Test
	@DisplayName("nextTag throws IllegalArgumentException if the reader is null")
	void test16() {
		assertThrows(IllegalArgumentException.class, () -> Util.nextTag(null));
	}

	@Test
	@DisplayName("nextTag returns the next tag's name")
	void test17() throws IllegalArgumentException, IOException {
		assertEquals(UtilTestData.TAG_NAME, Util.nextTag(reader));
	}

	@Test
	@DisplayName("nextTag throws IOException if the input does not start with tag")
	void test18() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.nextTag(readerWithWordBeforeTag));
	}

	@Test
	@DisplayName("nextTag reads until the end of the tag")
	void test19() throws IllegalArgumentException, IOException {
		Util.nextTag(reader);
		assertEquals(UtilTestData.AFTER_TAG, TestUtil.readerToString(reader));
	}

}
