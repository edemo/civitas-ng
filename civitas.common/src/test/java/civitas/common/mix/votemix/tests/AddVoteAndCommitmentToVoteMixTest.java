package civitas.common.mix.votemix.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.common.mix.AddCommitmentToMix;
import civitas.common.mix.votemix.AddVoteAndCommitmentToVoteMix;
import civitas.common.mix.votemix.AddVoteToVoteMix;
import civitas.common.tests.RandomAwareTestBase;
import civitas.common.tests.VoteTestData;

class AddVoteAndCommitmentToVoteMixTest extends RandomAwareTestBase implements VoteMixTestData, VoteTestData {

	@InjectMocks
	AddVoteAndCommitmentToVoteMix addVoteAndCommitmentToVoteMix;

	@Mock
	AddVoteToVoteMix addVoteToVoteMix;

	@Mock
	AddCommitmentToMix addCommitmentToMix;

	@Test
	@DisplayName("adds the vote to the mix and adds the commitment to the mix")
	void test() throws IllegalAccessException {
		addVoteAndCommitmentToVoteMix.apply(VOTE_MIX_MOCK, VOTE_MOCK, BYTES);
		verify(addCommitmentToMix).apply(VOTE_MIX_MOCK, BYTES);
		verify(addVoteToVoteMix).apply(VOTE_MIX_MOCK, VOTE_MOCK);
	}

	@Test
	@DisplayName("if the mix is null, a NullPointerException is thrown")
	void test1() {
		assertThrows(NullPointerException.class, () -> addVoteAndCommitmentToVoteMix.apply(null, VOTE_MOCK, BYTES));
	}

	@Test
	@DisplayName("if the vote is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class, () -> addVoteAndCommitmentToVoteMix.apply(VOTE_MIX_MOCK, null, BYTES));
	}

	@Test
	@DisplayName("if the commitments is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(
				NullPointerException.class, () -> addVoteAndCommitmentToVoteMix.apply(VOTE_MIX_MOCK, VOTE_MOCK, null));
	}
}
