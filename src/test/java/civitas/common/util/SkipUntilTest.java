package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.common.Util;

public class SkipUntilTest extends UtilTestBase {

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
				() -> Util.skipUntil(null, ESCAPED));
	}

	@Test
	@DisplayName("skipUntil throws IOException if the searched string is not in the content")
	void test21_2() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.skipUntil(reader, NOTFOUND_STRING));
	}

	@Test
	@DisplayName("skipUntil throws IOException if the searched string is just partially found")
	void test21_3() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> Util.skipUntil(reader, HALFFOUND_STRING));
	}

	@Test
	@DisplayName("skipUntil reads to after the string")
	void test22() throws IllegalArgumentException, IOException {
		Util.skipUntil(reader, FOUND_STRING);
		assertEquals(AFTER_IS, TestUtil.readerToString(reader));
	}

}
