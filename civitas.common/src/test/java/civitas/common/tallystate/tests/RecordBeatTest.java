package civitas.common.tallystate.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tallystate.RecordBeat;
import civitas.common.tallystate.TallyState;
import civitas.common.tests.RandomAwareTestBase;

class RecordBeatTest extends RandomAwareTestBase implements TallyStateTestData {

	@InjectMocks
	RecordBeat recordBeat;

	@DisplayName("records a beat between two candidates")
	@Test
	void test() {
		TallyState tallyState = TALLY_STATE_EMPTY_SUPPLIER.get();
		recordBeat.apply(tallyState, 0, 1);
		assertEquals(TALLY_STATE_0_BEATS_1, tallyState);
	}

	@DisplayName("if the first candidate index is < 0, an IllegalArgumentException is thrown")
	@Test
	void test1() {
		assertThrows(IllegalArgumentException.class, () -> recordBeat.apply(TALLY_STATE_EMPTY_SUPPLIER.get(), -1, 2));
	}

	@DisplayName("if the first candidate index is too big, an IllegalArgumentException is thrown")
	@Test
	void test2() {
		assertThrows(IllegalArgumentException.class, () -> recordBeat.apply(TALLY_STATE_EMPTY_SUPPLIER.get(), 3, 2));
	}

	@DisplayName("if the second candidate index <0, an IllegalArgumentException is thrown")
	@Test
	void test3() {
		assertThrows(IllegalArgumentException.class, () -> recordBeat.apply(TALLY_STATE_EMPTY_SUPPLIER.get(), 1, -2));
	}

	@DisplayName("if the second candidate index is too big, an IllegalArgumentException is thrown")
	@Test
	void test4() {
		assertThrows(IllegalArgumentException.class, () -> recordBeat.apply(TALLY_STATE_EMPTY_SUPPLIER.get(), 1, 3));
	}

	@DisplayName("if the two candidates are the same, an IllegalArgumentException is thrown")
	@Test
	void test5() {
		assertThrows(IllegalArgumentException.class, () -> recordBeat.apply(TALLY_STATE_EMPTY_SUPPLIER.get(), 1, 1));
	}
}
