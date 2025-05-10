package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

public class ReadUntilTest extends UtilTestBase {

	@Test
	@DisplayName("readUntil returns empty string for null string")
	void test20() throws IllegalArgumentException, IOException {
		assertEquals("", Util.readUntil(reader, null));
	}

	@Test
	@DisplayName("readUntil returns empty string for empty string")
	void test20_1() throws IllegalArgumentException, IOException {
		assertEquals("", Util.readUntil(reader, ""));
	}

	@Test
	@DisplayName("readUntil throws NullPointerException for null reader")
	void test21() throws IllegalArgumentException, IOException {
		assertThrows(NullPointerException.class,
				() -> Util.readUntil(null, ESCAPED));
	}

	@Test
	@DisplayName("readUntil throws IOException if the searched string is not in the content")
	void test21_2() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readUntil(reader, NOTFOUND_STRING));
	}

	@Test
	@DisplayName("readUntil throws IOException if the searched string is just partially found")
	void test21_3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readUntil(reader, HALFFOUND_STRING));
	}

	@Test
	@DisplayName("readUntil returns what is up to the string")
	void test19() throws IllegalArgumentException, IOException {
		assertEquals(UP_TO_FOUND_STRING, Util.readUntil(reader, FOUND_STRING));
	}

	@Test
	@DisplayName("readUntil reads to after the string")
	void test22() throws IllegalArgumentException, IOException {
		Util.readUntil(reader, FOUND_STRING);
		assertEquals(AFTER_IS, TestUtil.readerToString(reader));
	}

}
