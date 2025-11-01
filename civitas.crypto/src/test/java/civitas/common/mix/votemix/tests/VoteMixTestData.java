package civitas.common.mix.votemix.tests;

import static org.mockito.Mockito.mock;

import civitas.common.EncryptedVote;
import civitas.common.mix.votemix.VoteMix;
import civitas.common.tests.VoteTestData;

public interface VoteMixTestData extends VoteTestData {

	VoteMix VOTE_MIX_MOCK = mock(VoteMix.class);

	VoteMix FROM_MIX = VoteMix.builder()
			.number(5)
			.mixNonceHash("".getBytes())
			.commitments(new byte[][] {})
			.votes(new EncryptedVote[] {FROM_VOTE})
			.build();
	VoteMix TO_MIX = VoteMix.builder()
			.number(6)
			.mixNonceHash("".getBytes())
			.commitments(new byte[][] {})
			.votes(new EncryptedVote[] {TO_VOTE_BAD_CAP, TO_VOTE})
			.build();
}
