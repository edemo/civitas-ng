package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.common.UtilTestData;

public class SkipUntilTest {
	public static StringReader reader;

	public static StringReader readerMock = mock(StringReader.class);

	@BeforeEach
	private void setUp() {
		reader = new StringReader(UtilTestData.READER_CONTENT);
	}

	@Test
	@DisplayName("skipUntil silently does nothing for null string")
	void test20() throws IllegalArgumentException, IOException {
		Util.skipUntil(readerMock, null);
		verifyNoInteractions(readerMock);
	}

	@Test
	@DisplayName("skipUntil silently does nothing for empty string")
	void test20_1() throws IllegalArgumentException, IOException {
		Util.skipUntil(readerMock, "");
		verifyNoInteractions(readerMock);
	}

	@Test
	@DisplayName("skipUntil throws NullPointerException for null reader")
	void test21() throws IllegalArgumentException, IOException {
		assertThrows(NullPointerException.class,
				() -> Util.skipUntil(null, UtilTestData.ESCAPED));
	}

	@Test
	@DisplayName("skipUntil throws IOException if the searched string is not in the content")
	void test21_2() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.skipUntil(reader, UtilTestData.NOTFOUND_STRING));
	}

	@Test
	@DisplayName("skipUntil throws IOException if the searched string is just partially found")
	void test21_3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.skipUntil(reader, UtilTestData.HALFFOUND_STRING));
	}

	@Test
	@DisplayName("skipUntil reads to after the string")
	void test22() throws IllegalArgumentException, IOException {
		Util.skipUntil(reader, UtilTestData.FOUND_STRING);
		assertEquals(UtilTestData.AFTER_IS, TestUtil.readerToString(reader));
	}

}
