package civitas.common.ballot;

import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.util.Use;
import lombok.NonNull;

public class RecordOnePairInBallot {

	@Use
	CalculatePositionInBallot calculatePositionInBallot;

	public void apply(@NonNull Ballot that, int i, int j, int voteChoice) {
		if ((0 > i) || (i >= j) || (j >= that.k))
			throw new IllegalArgumentException();
		that.matrix[calculatePositionInBallot.apply(i, j, that.k)] = voteChoice;
	}

}
