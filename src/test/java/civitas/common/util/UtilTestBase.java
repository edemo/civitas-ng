package civitas.common.util;

import static org.mockito.Mockito.mock;

import java.io.PrintWriter;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;

import civitas.common.UtilTestData;

public class UtilTestBase extends UtilTestData {

	StringReader reader;
	StringReader readerWithWordBeforeTag;
	StringReader readerEndTag;
	StringReader readerSimpleTag;
	StringReader readerEndTagWhitLeadingWhiteSpace;
	StringReader readerSimpleTagWithAiryTag;
	StringReader readerTagNotEndingWell;
	StringReader readerSimpleTagEmpty;
	StringReader readerSimpleTagNothingIn;
	StringReader readerSimpleTagNoClosing;
	StringReader readerSimpleTagInt;
	StringReader readerSimpleTagYes;
	StringReader readerSimpleTagY;
	StringReader readerSimpleTagTrue;
	StringReader readerSimpleTagYesMixed;
	StringReader readerSimpleTagYCaps;
	StringReader readerSimpleTagTrueMixed;
	StringReader readerWithAllWhiteSpace;
	StringReader readerWhitLeadingWhiteSpace;

	StringReader readerMock;
	PrintWriter printWriterMock;
	StringReader readerTagWhitLeadingWhiteSpace;
	StringReader readerTagCut;
	StringReader readerTagDifferentEnd;

	@BeforeEach
	void setUp() {
		reader = new StringReader(READER_CONTENT);
		readerWithWordBeforeTag = new StringReader(
				READER_CONTENT_WITH_WORD_BEFORE_TAG);
		readerTagWhitLeadingWhiteSpace = new StringReader(
				ALL_WHITESPACE + READER_CONTENT);
		readerEndTag = new StringReader(READER_CONTENT_ENDTAG);
		readerSimpleTag = new StringReader(READER_CONTENT_SIMPLE_TAG);
		readerEndTagWhitLeadingWhiteSpace = new StringReader(
				ALL_WHITESPACE + READER_CONTENT_ENDTAG);
		readerSimpleTagWithAiryTag = new StringReader(
				AIRY_TAG + FOUND_STRING + ENDTAG);
		readerTagNotEndingWell = new StringReader(TAG_START);
		readerTagCut = new StringReader(TAG_CUT);
		readerTagDifferentEnd = new StringReader(TAG_DIFFERENT_END);
		readerSimpleTagEmpty = new StringReader(EMPTY_TAG);
		readerSimpleTagNothingIn = new StringReader(TAG_WITH_NOTHING);
		readerSimpleTagNoClosing = new StringReader(TAG + FOUND_STRING);
		readerSimpleTagInt = new StringReader(TAG_WITH_INT);
		readerSimpleTagYes = new StringReader(TAG_WITH_YES);
		readerSimpleTagY = new StringReader(TARG_WITH_Y);
		readerSimpleTagTrue = new StringReader(TAG_WITH_TRUE);
		readerSimpleTagYesMixed = new StringReader(TAG_WITH_YES_MIXED);
		readerSimpleTagYCaps = new StringReader(TAG_WITH_Y_CAPS);
		readerSimpleTagTrueMixed = new StringReader(TAG_WITH_TRUE_MIXED);
		readerWhitLeadingWhiteSpace = new StringReader(
				ALL_WHITESPACE + READER_CONTENT);
		readerWithAllWhiteSpace = new StringReader(ALL_WHITESPACE);

		readerMock = mock(StringReader.class);
		printWriterMock = mock(PrintWriter.class);

	}

}