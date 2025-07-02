package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class GetBaseContextTest extends TestBase implements ElectionDetailsTestData {

	@InjectMocks
	GetBaseContext getBaseContext;

	@Test
	@DisplayName("the base context is comprised of the election id string and the voter block")
	void test() {
		assertEquals(ELECTION_ID_STRING + ":14:",
				getBaseContext.apply(ELECTION_DETAILS, 14));
	}

}
