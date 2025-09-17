package civitas.common.mix.votemix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.VoteTestData;

class AddVoteToVoteMixTest extends RandomAwareTestBase
		implements VoteMixTestData, VoteTestData {
	@InjectMocks
	AddVoteToVoteMix addVoteToVoteMix;

	@Test
	@DisplayName("adds the vote to the end of votes")
	void test() {
		VoteMix mix = FROM_MIX.toBuilder().build();
		addVoteToVoteMix.apply(mix, VOTE_MOCK);
		assertEquals(VOTE_MOCK, mix.votes[mix.votes.length - 1]);
	}

	@Test
	@DisplayName("if the mix is null, throws NullPointerException")
	void test1() {
		assertThrows(NullPointerException.class,
				() -> addVoteToVoteMix.apply(null, VOTE_MOCK));
	}

	@Test
	@DisplayName("if the vote is null, throws NullPointerException")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> addVoteToVoteMix.apply(VOTE_MIX_MOCK, null));
	}

}
