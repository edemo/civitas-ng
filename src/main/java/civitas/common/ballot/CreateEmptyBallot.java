package civitas.common.ballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.ballotdesign.CalculateBallotLength;

@Service
public class CreateEmptyBallot {

	@Autowired
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
