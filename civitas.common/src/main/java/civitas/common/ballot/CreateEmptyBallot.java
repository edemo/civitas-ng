package civitas.common.ballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.VoteChoice;
import civitas.common.ballotdesign.CalculateBallotLength;

@Controller
public class CreateEmptyBallot {

	@Autowired
	CalculateBallotLength calculateBallotLength;

	public Ballot apply(final int numCandidates) {
		if (numCandidates < 2) {
			throw new IllegalArgumentException("A ballot must contain at least one candidate and none of above");
		}
		int size = calculateBallotLength.apply(numCandidates);
		VoteChoice[] matrix = new VoteChoice[size];
		return new Ballot(size, matrix);
	}
}
