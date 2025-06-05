package civitas.common.ballot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

public class BallotToXMLTest extends TestBase implements BallotTestData {

	@Tested
	BallotToXML ballotToXML;

	@Test
	@DisplayName("sends the XML representation of the ballot to the writer")
	void test() {
		StringWriter sw = new StringWriter();
		ballotToXML.apply(BALLOT, new PrintWriter(sw));
		assertEquals(BALLOT_XML, sw.toString());
	}

	@Test
	@DisplayName("if the ballot is null, a NullPointerException is thrown")
	void test1() {
		StringWriter sw = new StringWriter();
		assertThrows(NullPointerException.class,
				() -> ballotToXML.apply(null, new PrintWriter(sw)));
	}

	@Test
	@DisplayName("if the writer is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> ballotToXML.apply(BALLOT, null));
	}

}
