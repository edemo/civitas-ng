package civitas.common.tallystate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class CreateTallyStateTest extends TestBase implements TallyStateTestData {

	@InjectMocks
	CreateTallyState createTallyState;

	@DisplayName("creates a tally state the sized to the number of candidates")
	@Test
	void test() {
		assertEquals(CANDIDATES.size(),
				createTallyState.newTallyState(BALLOTDESIGN).size);
	}

	@DisplayName("the number of rows in the matrix is the number of candidates")
	@Test
	void test1() {
		assertEquals(CANDIDATES.size(),
				createTallyState.newTallyState(BALLOTDESIGN).matrix.length);
	}

	@DisplayName("the number of columns in the matrix is the number of candidates")
	@Test
	void test2() {
		assertEquals(CANDIDATES.size(),
				createTallyState.newTallyState(BALLOTDESIGN).matrix[0].length);
	}

}
