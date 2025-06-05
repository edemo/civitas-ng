package civitas.common.tallystate;

import civitas.common.ballotdesign.BallotDesignTestData;

public interface TallyStateTestData extends BallotDesignTestData {

	TallyState TALLY_STATE = new TallyState(CANDIDATES.size(),
			new Integer[CANDIDATES.size()][CANDIDATES.size()]);
}
