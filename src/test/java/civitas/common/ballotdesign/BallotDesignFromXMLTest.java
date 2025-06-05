package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class BallotDesignFromXMLTest extends TestBase implements BallotDesignTestData {

	@Tested
	BallotDesignFromXML ballotDesignFromXML;

	@Test
	void test() throws IllegalArgumentException, IOException {
		assertEquals(BALLOTDESIGN,
				ballotDesignFromXML.apply(new StringReader(BALLOTDESIGN_XML)));
	}

}
