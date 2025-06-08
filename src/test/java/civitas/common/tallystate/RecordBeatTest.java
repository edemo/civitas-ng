package civitas.common.tallystate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class RecordBeatTest extends TestBase implements TallyStateTestData {

	@Tested
	RecordBeat recordBeat;

	@DisplayName("records a beat between two candidates")
	@Test
	void test() {
		recordBeat.apply(TALLY_STATE_EMPTY, 1, 2);
		assertEquals(TALLY_STATE_RECORDED, TALLY_STATE_EMPTY);
	}

	@DisplayName("if the first candidate index is < 0, an IllegalArgumentException is thrown")
	@Test
	void test1() {
		assertThrows(IllegalArgumentException.class,
				() -> recordBeat.apply(TALLY_STATE_EMPTY, -1, 2));
	}

	@DisplayName("if the first candidate index is too big, an IllegalArgumentException is thrown")
	@Test
	void test2() {
		assertThrows(IllegalArgumentException.class,
				() -> recordBeat.apply(TALLY_STATE_EMPTY, 3, 2));
	}

	@DisplayName("if the second candidate index <0, an IllegalArgumentException is thrown")
	@Test
	void test3() {
		assertThrows(IllegalArgumentException.class,
				() -> recordBeat.apply(TALLY_STATE_EMPTY, 1, -2));
	}

	@DisplayName("if the second candidate index is too big, an IllegalArgumentException is thrown")
	@Test
	void test4() {
		assertThrows(IllegalArgumentException.class,
				() -> recordBeat.apply(TALLY_STATE_EMPTY, 1, 3));
	}

	@DisplayName("if the two candidates are the same, an IllegalArgumentException is thrown")
	@Test
	void test5() {
		assertThrows(IllegalArgumentException.class,
				() -> recordBeat.apply(TALLY_STATE_EMPTY, 1, 1));
	}

}
