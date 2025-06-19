package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class GetElectionIdAsStringTest extends TestBase implements ElectionTestData {

	@InjectMocks
	GetElectionIdAsString getElectionIdAsString;

	@Test
	@DisplayName("just the election host, port and id string joined by colons")
	void test() {
		assertEquals(ELECTION_ID_AS_STRING,
				getElectionIdAsString.apply(ELECTION_ID));
	}

}
