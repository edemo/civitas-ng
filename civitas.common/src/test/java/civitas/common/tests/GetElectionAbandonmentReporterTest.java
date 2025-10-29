package civitas.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.election.GetElectionAbandonmentReporter;
import civitas.common.election.tests.ElectionAbandonmentTestData;

class GetElectionAbandonmentReporterTest extends RandomAwareTestBase implements ElectionAbandonmentTestData {

	@InjectMocks
	GetElectionAbandonmentReporter getElectionAbandonmentReporter;

	@Test
	@DisplayName("in case of tabulation teller, the string contains that fact and the index of the teller")
	void test() {
		assertEquals(ELECTION_ABANDONMENT_REPORTER, getElectionAbandonmentReporter.apply(ELECTION_ABANDONMENT));
	}

	@Test
	@DisplayName("otherwise gives 'unknown entity'")
	void test1() {
		assertEquals(
				ELECTION_ABANDONMENT_REPORTER_UNKNOWN,
				getElectionAbandonmentReporter.apply(ELECTION_ABANDONMENT_NONTELLER));
	}
}
