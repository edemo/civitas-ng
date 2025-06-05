package civitas.common.ballot;

import civitas.common.ballotdesign.CalculateBallotLength;
import civitas.util.Use;

public class CreateEmptyBallot {

	@Use
	CalculateBallotLength calculateBallotLength;

	public Ballot apply(int numCandidates) {
		if (numCandidates < 2)
			throw new IllegalArgumentException(
					"A ballot must contain at least one candidate and none of above");
		int size = calculateBallotLength.apply(numCandidates);
		int[] matrix = new int[size];
		return new Ballot(size, matrix);
	}

}
