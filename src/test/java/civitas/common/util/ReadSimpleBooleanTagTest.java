package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class ReadSimpleBooleanTagTest extends UtilTestBase {

	@Test
	@DisplayName("readSimpleBooleanTag returns true if the contents of the tag is \"yes\"")
	void test1() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagYes, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag returns true if the contents of the tag is \"y\"")
	void test2() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagY, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag returns true if the contents of the tag is \"true\"")
	void test3() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagTrue, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag does not care about the case of \"yes\"")
	void test5() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagYesMixed, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag does not care about the case of \"y\"")
	void test6() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagYCaps, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag does not care about the case of \"true\"")
	void test7() throws IllegalArgumentException, IOException {
		assertTrue(Util.readSimpleBooleanTag(readerSimpleTagTrueMixed, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleBooleanTag returns false otherwise")
	void test4() throws IllegalArgumentException, IOException {
		assertFalse(Util.readSimpleBooleanTag(readerSimpleTag, TAG_NAME));
	}

}
