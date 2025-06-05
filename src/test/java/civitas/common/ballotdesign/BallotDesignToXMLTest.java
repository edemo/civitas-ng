package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class BallotDesignToXMLTest extends TestBase implements BallotDesignTestData {

	@Tested
	BallotDesignToXML ballotDesignToXML;

	@Test
	@DisplayName("sends the ballot design to the writer")
	void test() {
		StringWriter sw = new StringWriter();
		ballotDesignToXML.apply(BALLOTDESIGN, new PrintWriter(sw));
		assertEquals(BALLOTDESIGN_XML, sw.toString());
	}

}
