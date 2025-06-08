package civitas.common.tallystate;

import civitas.common.ballotdesign.BallotDesignTestData;

public interface TallyStateTestData extends BallotDesignTestData {

	TallyState TALLY_STATE_EMPTY = new TallyState(CANDIDATES.size(),
			new Integer[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } });

	TallyState TALLY_STATE_RECORDED = new TallyState(CANDIDATES.size(),
			new Integer[][] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 0, 0 } });
}
