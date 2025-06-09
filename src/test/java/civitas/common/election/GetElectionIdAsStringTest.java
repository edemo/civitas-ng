package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetElectionIdAsStringTest extends TestBase implements ElectionTestData {

	@Tested
	GetElectionIdAsString getElectionIdAsString;

	@Test
	@DisplayName("just the election host, port and id string joined by colons")
	void test() {
		assertEquals(ELECTION_ID_AS_STRING,
				getElectionIdAsString.apply(ELECTION_ID));
	}

}
