package civitas.common.util;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;

import civitas.common.UtilTestData;

public class UtilTestBase {

	StringReader reader;
	StringReader readerWithWordBeforeTag;
	StringReader readerEndTag;
	StringReader readerEndTagWithAllWhiteSpace;
	StringReader readerEndTagWhitLeadingWhiteSpace;

	@BeforeEach
	private void setUp() {
		reader = new StringReader(UtilTestData.READER_CONTENT);
		readerWithWordBeforeTag = new StringReader(
				UtilTestData.READER_CONTENT_WITH_WORD_BEFORE_TAG);
		readerEndTag = new StringReader(UtilTestData.READER_CONTENT_ENDTAG);
		readerEndTagWhitLeadingWhiteSpace = new StringReader(
				UtilTestData.ALL_WHITESPACE + UtilTestData.READER_CONTENT_ENDTAG);
		readerEndTagWithAllWhiteSpace = new StringReader(
				UtilTestData.ALL_WHITESPACE);

	}
}