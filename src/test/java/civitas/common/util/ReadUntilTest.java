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

public class ReadUntilTest {

	public static StringReader reader;

	@BeforeEach
	private void setUp() {
		reader = new StringReader(UtilTestData.READER_CONTENT);
	}

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
				() -> Util.readUntil(null, UtilTestData.ESCAPED));
	}

	@Test
	@DisplayName("readUntil throws IOException if the searched string is not in the content")
	void test21_2() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readUntil(reader, UtilTestData.NOTFOUND_STRING));
	}

	@Test
	@DisplayName("readUntil throws IOException if the searched string is just partially found")
	void test21_3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.readUntil(reader, UtilTestData.HALFFOUND_STRING));
	}

	@Test
	@DisplayName("readUntil returns what is up to the string")
	void test19() throws IllegalArgumentException, IOException {
		assertEquals("<div> ", Util.readUntil(reader, UtilTestData.FOUND_STRING));
	}

	@Test
	@DisplayName("readUntil reads to after the string")
	void test22() throws IllegalArgumentException, IOException {
		Util.readUntil(reader, UtilTestData.FOUND_STRING);
		assertEquals(" the next tag", TestUtil.readerToString(reader));
	}

}
