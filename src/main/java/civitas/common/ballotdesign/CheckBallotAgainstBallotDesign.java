package civitas.common.ballotdesign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.VoteChoice;
import civitas.common.ballot.Ballot;

@Controller
public class CheckBallotAgainstBallotDesign implements CommonConstants {

	@Autowired
	CalculateBallotLength calculateBallotLength;
	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;

	public void apply(BallotDesign that, Ballot b)
			throws IllegalArgumentException {

		if (b.k != that.candidates.length
				|| b.matrix.length != calculateBallotLength.apply(b.k)) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}
		for (int i = 0; i < b.k; i++) {
			for (int j = i + 1; j < b.k; j++) {
				VoteChoice choice = b.matrix[calculatePositionInBallot.apply(i, j,
						b.k)];
				if (null == choice) {
					throw new IllegalArgumentException(
							"Illegal choice for (" + i + "," + j + ")");
				}

			}
		}

	}

}
