package civitas.common.mix.hashrevelation;

import civitas.common.election.ElectionTestData;

public interface MixHashRevelationTestData extends ElectionTestData {
	String MIX_HASH_REVELATION_VOTE = "mixHashRevelation:vote:" + BLOCKNAME_14
			+ ":2";
	String MIX_HASH_REVELATION_ROLL = "mixHashRevelation:elecRoll:" + BLOCKNAME_14
			+ ":2";

}
