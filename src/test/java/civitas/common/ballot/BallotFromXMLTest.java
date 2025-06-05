package civitas.common.ballot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class BallotFromXMLTest extends TestBase implements BallotTestData {

	@Tested
	BallotFromXML ballotFromXML;

	@Test
	@DisplayName("creates a ballot from XML")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(BALLOT, ballotFromXML.apply(new StringReader(BALLOT_XML)));
	}

	@Test
	@DisplayName("if the xml does not contain the expected pairs, an IOException is thrown (version j)")
	void test1() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> ballotFromXML.apply(new StringReader(BALLOT_XML_BAD_PAIR)));
	}

	@Test
	@DisplayName("if the xml does not contain the expected pairs, an IOException is thrown (version i)")
	void test2() throws IllegalArgumentException, IOException {
		assertThrows(IOException.class,
				() -> ballotFromXML.apply(new StringReader(BALLOT_XML_BAD_PAIR2)));
	}

}
