package civitas.common.ballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.ballotdesign.CalculatePositionInBallot;
import lombok.NonNull;

@Service
public class RecordOnePairInBallot {

	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;

	public void apply(@NonNull Ballot that, int i, int j, int voteChoice) {
		if ((0 > i) || (i >= j) || (j >= that.k))
			throw new IllegalArgumentException();
		that.matrix[calculatePositionInBallot.apply(i, j, that.k)] = voteChoice;
	}

}
