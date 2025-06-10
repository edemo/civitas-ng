package civitas.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.election.ElectionTestData;
import civitas.common.election.GetElectionAbandonmentReporter;
import civitas.util.Tested;

class GetElectionAbandonmentReporterTest extends TestBase
		implements ElectionTestData {

	@Tested
	GetElectionAbandonmentReporter getElectionAbandonmentReporter;

	@Test
	@DisplayName("in case of tabulation teller, the string contains that fact and the index of the teller")
	void test() {
		assertEquals(ELECTION_ABANDONMENT_REPORTER,
				getElectionAbandonmentReporter.apply(ELECTION_ABANDONMENT));
	}

	@Test
	@DisplayName("otherwise gives 'unknown entity'")
	void test1() {
		assertEquals(ELECTION_ABANDONMENT_REPORTER_UNKNOWN,
				getElectionAbandonmentReporter.apply(ELECTION_ABANDONMENT_NONTELLER));
	}

}
