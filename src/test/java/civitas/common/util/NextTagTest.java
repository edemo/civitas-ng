package civitas.common.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

class NextTagTest {

	@Test
	@DisplayName("nextTag throws IllegalArgumentException if the reader is null")
	void test16() {
		assertThrows(IllegalArgumentException.class, () -> Util.nextTag(null));
	}

	@Test
	@DisplayName("nextTag returns the next tag's name")
	void test17() throws IllegalArgumentException, IOException {
		assertEquals("div", Util.nextTag(new StringReader("<div> is the next tag")));
	}

	@Test
	@DisplayName("nextTag throws IOException if the input does not start with tag")
	void test18() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class, () -> Util.nextTag(new StringReader("but <div> is the next tag")));
	}

	@Test
	@DisplayName("nextTag reads until the end of the tag")
	void test19() throws IllegalArgumentException, IOException {
		StringReader r = new StringReader("<div> is the next tag");
		Util.nextTag(r);
		assertEquals(" is the next tag", TestUtil.readerToString(r));
	}

}
