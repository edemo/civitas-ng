package civitas.common.mix.hashrevelation.tests;

import civitas.common.ballotdesign.tests.BallotDesignTestData;
import civitas.common.election.tests.ElectionIdTestData;

public interface MixHashRevelationTestData extends ElectionIdTestData, BallotDesignTestData {
	String BLOCKNAME_14 = "voterBlock-4-context-" + BARE_CONTEXT_2;
	String FULL_CONTEXT_11 = ELECTION_ID_STRING + ":3:" + BARE_CONTEXT_2;
	String FULL_CONTEXT_14 = ELECTION_ID_STRING + ":4:" + BARE_CONTEXT_2;

	String MIX_HASH_REVELATION_VOTE = "mixHashRevelation:vote:" + BLOCKNAME_14 + ":2";
	String MIX_HASH_REVELATION_ROLL = "mixHashRevelation:elecRoll:" + BLOCKNAME_14 + ":2";
}
