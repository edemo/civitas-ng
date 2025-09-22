package civitas.common.electionresults;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class GetMetaForTellerTest extends RandomAwareTestBase
		implements TellerTestData {

	@InjectMocks
	GetMetaForTeller getMetaForTeller;

	@Test
	@DisplayName("returns 'ElectionResults:Teller<n>'")
	void test() {
		assertEquals(TELLER_META, getMetaForTeller.appply(TELLER_INDEX));
	}

}
