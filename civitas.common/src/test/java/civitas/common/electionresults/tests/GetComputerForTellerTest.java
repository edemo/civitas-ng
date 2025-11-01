package civitas.common.electionresults.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.electionresults.GetComputerForTeller;
import civitas.common.tests.RandomAwareTestBase;

class GetComputerForTellerTest extends RandomAwareTestBase implements TellerTestData {

	@InjectMocks
	GetComputerForTeller getComputerForTeller;

	@Test
	@DisplayName("returns 'Teller<n>'")
	void test() {
		assertEquals(TELLER_COMPUTER, getComputerForTeller.apply(TELLER_INDEX));
	}
}
