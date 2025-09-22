package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.ballot.BallotTestData;
import civitas.common.tallystate.TallyState;
import civitas.common.tallystate.TallyStateTestData;
import civitas.common.votersubmission.VoterSubmissionTestData;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElgamalMsgTestData;

class TallyVoteTest extends RandomAwareTestBase
		implements TallyStateTestData, VoterSubmissionTestData, BallotTestData, ElgamalMsgTestData {

	@InjectMocks
	TallyVote tallyVote;

	@Test
	@DisplayName("tallies one message\n" + " - if i beats j, then matrix[i][j] is incremented")
	void test() {
		TallyState tallyState = TALLY_STATE_EMPTY_SUPPLIER.get();
		tallyVote.apply(ADDITIONALENV, new ElGamalMsg(I_BEATS_J_ENCODED), CONTEXT_0, tallyState, DECODEMAP);
		assertEquals(TALLY_STATE_0_BEATS_1, tallyState);
	}

	@Test
	@DisplayName(" - if j beats i, then matrix[j][i] is incremented")
	void test_1() {
		TallyState tallyState = TALLY_STATE_EMPTY_SUPPLIER.get();
		tallyVote.apply(ADDITIONALENV, new ElGamalMsg(J_BEATS_I_ENCODED), CONTEXT_0, tallyState, DECODEMAP);
		assertEquals(TALLY_STATE_1_BEATS_0, tallyState);
	}

	@Test
	@DisplayName("if current context is null, then an IllegalArgumentException is thrown")
	void test1() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(
						ADDITIONALENV,
						new ElGamalMsg(I_BEATS_J_ENCODED),
						null,
						TALLY_STATE_EMPTY_SUPPLIER.get(),
						DECODEMAP));
	}

	@Test
	@DisplayName("if the encrypted choice is not in the decode map, then an IllegalArgumentException is thrown")
	void test1_2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(
						ADDITIONALENV,
						new ElGamalMsg(BIGINT_A),
						CONTEXT_0,
						TALLY_STATE_EMPTY_SUPPLIER.get(),
						DECODEMAP));
	}

	@Test
	@DisplayName("if current context is not staring with additional env, then an IllegalArgumentException is thrown")
	void test2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(
						ADDITIONALENV,
						new ElGamalMsg(I_BEATS_J_ENCODED),
						BARE_CONTEXT_0,
						TALLY_STATE_EMPTY_SUPPLIER.get(),
						DECODEMAP));
	}

	@Test
	@DisplayName("if i and j is not in current context, then an IllegalArgumentException is thrown")
	void test2_1() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(
						ADDITIONALENV,
						new ElGamalMsg(I_BEATS_J_ENCODED),
						CONTEXT_BAD,
						TALLY_STATE_EMPTY_SUPPLIER.get(),
						DECODEMAP));
	}

	@Test
	@DisplayName("if the current context contain text instead of number, then an IllegalArgumentException is thrown")
	void test2_2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(
						ADDITIONALENV,
						new ElGamalMsg(I_BEATS_J_ENCODED),
						CONTEXT_BAD_NOINT,
						TALLY_STATE_EMPTY_SUPPLIER.get(),
						DECODEMAP));
	}

	@Test
	@DisplayName("if msg is null, then an IllegalArgumentException is thrown")
	void test3() {
		assertThrows(
				IllegalArgumentException.class,
				() -> tallyVote.apply(ADDITIONALENV, null, CONTEXT_0, TALLY_STATE_EMPTY_SUPPLIER.get(), DECODEMAP));
	}
}
