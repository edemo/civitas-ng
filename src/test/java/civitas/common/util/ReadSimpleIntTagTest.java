package civitas.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;

public class ReadSimpleIntTagTest extends UtilTestBase {

	@Test
	@DisplayName("readSimpleIntTag returns the content of the tag as an int")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(42, Util.readSimpleIntTag(readerSimpleTagInt, TAG_NAME));
	}

	@Test
	@DisplayName("readSimpleIntTag throws IllegalArgumentException if the string in the tag cannot be read as an int")
	void test2() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> Util.readSimpleIntTag(readerSimpleTag, TAG_NAME));
	}

}
