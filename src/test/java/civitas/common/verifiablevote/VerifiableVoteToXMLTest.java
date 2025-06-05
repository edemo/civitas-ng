package civitas.common.verifiablevote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class VerifiableVoteToXMLTest extends TestBase
		implements VerifiableVoteTestData {

	@Tested
	VerifiableVoteToXML verifiableVoteToXML;

	@Test
	@DisplayName("writes the vote to the writer in XML form")
	void test() {
		StringWriter sw = new StringWriter();
		verifiableVoteToXML.apply(VERIFIABLE_VOTE, new PrintWriter(sw));
		assertEquals(VERIFIABLE_VOTE_XML, sw.toString());
	}

}
