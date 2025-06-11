package civitas.common.mix.votemix;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.VoteTestData;
import civitas.util.Tested;

class AddVoteAndCommitmentToVoteMixTest extends TestBase
		implements VoteMixTestData, VoteTestData {

	@Tested
	AddVoteAndCommitmentToVoteMix addVoteAndCommitmentToVoteMix;

	@Test
	@DisplayName("adds the vote to the mix and adds the commitment to the mix")
	void test() {
		addVoteAndCommitmentToVoteMix.apply(VOTE_MIX_MOCK, VOTE_MOCK, BYTES);
		verify(addVoteAndCommitmentToVoteMix.addCommitmentToMix)
				.apply(VOTE_MIX_MOCK, BYTES);
		verify(addVoteAndCommitmentToVoteMix.addVoteToVoteMix).apply(VOTE_MIX_MOCK,
				VOTE_MOCK);
	}

	@Test
	@DisplayName("if the mix is null, a NullPointerException is thrown")
	void test1() {
		assertThrows(NullPointerException.class,
				() -> addVoteAndCommitmentToVoteMix.apply(null, VOTE_MOCK, BYTES));

	}

	@Test
	@DisplayName("if the vote is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> addVoteAndCommitmentToVoteMix.apply(VOTE_MIX_MOCK, null, BYTES));

	}

	@Test
	@DisplayName("if the commitments is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(NullPointerException.class, () -> addVoteAndCommitmentToVoteMix
				.apply(VOTE_MIX_MOCK, VOTE_MOCK, null));

	}
}
