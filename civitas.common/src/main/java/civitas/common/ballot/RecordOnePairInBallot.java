package civitas.common.ballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.VoteChoice;
import civitas.common.ballotdesign.CalculatePositionInBallot;
import jakarta.annotation.Nonnull;

@Controller
public class RecordOnePairInBallot {

	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;

	public void apply(@Nonnull final Ballot that, final int i, final int j, final VoteChoice voteChoice) {
		if (0 > i || i >= j || j >= that.k) {
			throw new IllegalArgumentException();
		}
		that.matrix[calculatePositionInBallot.apply(i, j, that.k)] = voteChoice;
	}
}
