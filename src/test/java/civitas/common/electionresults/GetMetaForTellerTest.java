package civitas.common.electionresults;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetMetaForTellerTest extends TestBase implements TellerTestData {

	@Tested
	GetMetaForTeller getMetaForTeller;

	@Test
	@DisplayName("returns 'ElectionResults:Teller<n>'")
	void test() {
		assertEquals(TELLER_META, getMetaForTeller.appply(TELLER_INDEX));
	}

}
