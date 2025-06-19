package civitas.common.ballot;

import civitas.common.CommonConstants;

public interface BallotTestData extends CommonConstants {

	Ballot BALLOT_EMPTY = new Ballot(3, new int[3]);

	Ballot BALLOT_ONE_RECORD = new Ballot(3,
			new int[] { 0, VOTE_CHOICE_I_BEATS_J, 0 });

	int VOTER_BLOCK = 3;
	int NUM_CANDIDATES = 3;
	Ballot BALLOT = new Ballot(NUM_CANDIDATES,
			new int[] {
					VOTE_CHOICE_I_BEATS_J,
					VOTE_CHOICE_J_BEATS_I,
					VOTE_CHOICE_NEITHER_BEAT });

	Ballot BALLOT_SHORT_MATRIX = new Ballot(NUM_CANDIDATES,
			new int[] { VOTE_CHOICE_I_BEATS_J, VOTE_CHOICE_J_BEATS_I });

	Ballot BALLOT_2_CANDIDATES = new Ballot(2,
			new int[] { VOTE_CHOICE_I_BEATS_J });

}
