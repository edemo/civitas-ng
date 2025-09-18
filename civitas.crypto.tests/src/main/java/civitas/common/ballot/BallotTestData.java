package civitas.common.ballot;

import java.util.function.Supplier;

import civitas.common.VoteChoice;

public interface BallotTestData {

	Supplier<Ballot> BALLOT_EMPTY = () -> new Ballot(3, new VoteChoice[3]);

	Ballot BALLOT_ONE_RECORD = new Ballot(3, new VoteChoice[] {null, VoteChoice.I_BEATS_J, null});

	int VOTER_BLOCK = 3;
	int NUM_CANDIDATES = 3;
	Ballot BALLOT = new Ballot(
			NUM_CANDIDATES, new VoteChoice[] {VoteChoice.I_BEATS_J, VoteChoice.J_BEATS_I, VoteChoice.NEITHER_BEAT});

	Ballot BALLOT_SHORT_MATRIX =
			new Ballot(NUM_CANDIDATES, new VoteChoice[] {VoteChoice.I_BEATS_J, VoteChoice.J_BEATS_I});

	Ballot BALLOT_2_CANDIDATES = new Ballot(2, new VoteChoice[] {VoteChoice.I_BEATS_J});
}
