package civitas.common.tallystate.tests;

import java.util.function.Supplier;

import civitas.common.ballotdesign.tests.BallotDesignTestData;
import civitas.common.tallystate.TallyState;

public interface TallyStateTestData extends BallotDesignTestData {

	Supplier<TallyState> TALLY_STATE_EMPTY_SUPPLIER = new Supplier<>() {
		@Override
		public TallyState get() {
			return new TallyState(CANDIDATES.size(), new Integer[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}});
		}
	};

	TallyState TALLY_STATE_0_BEATS_1 =
			new TallyState(CANDIDATES.size(), new Integer[][] {{0, 1, 0}, {0, 0, 0}, {0, 0, 0}});

	TallyState TALLY_STATE_1_BEATS_0 =
			new TallyState(CANDIDATES.size(), new Integer[][] {{0, 0, 0}, {1, 0, 0}, {0, 0, 0}});
}
