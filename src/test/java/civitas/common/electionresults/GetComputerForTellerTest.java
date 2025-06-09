package civitas.common.electionresults;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetComputerForTellerTest extends TestBase implements TellerTestData {

	@Tested
	GetComputerForTeller getComputerForTeller;

	@Test
	@DisplayName("returns 'Teller<n>'")
	void test() {
		assertEquals(TELLER_COMPUTER, getComputerForTeller.apply(TELLER_INDEX));
	}

}
