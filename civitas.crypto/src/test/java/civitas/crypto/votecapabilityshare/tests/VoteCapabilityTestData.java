package civitas.crypto.votecapabilityshare.tests;

import java.util.List;
import java.util.Map;

import civitas.common.ballotdesign.tests.BallotDesignTestData;
import civitas.crypto.msg.tests.ElgamalMsgTestData;
import civitas.crypto.votecapability.VoteCapability;

public interface VoteCapabilityTestData extends ElgamalMsgTestData, BallotDesignTestData {
	VoteCapability VOTE_CAPABILITY = new VoteCapability(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	List<VoteCapability> VOTE_CAPABILITIES = RANDOMS.subList(0, 3).stream()
			.map(x -> new VoteCapability(BIGINT_G.modPow(x, BIGINT_P)))
			.toList();

	Map<String, VoteCapability> CAPABILITY_MAP = Map.of(
			CONTEXT_0,
			VOTE_CAPABILITIES.getFirst(),
			CONTEXT_1,
			VOTE_CAPABILITIES.get(1),
			CONTEXT_2,
			VOTE_CAPABILITIES.get(2));
}
