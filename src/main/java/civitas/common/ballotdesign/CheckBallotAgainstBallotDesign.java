package civitas.common.ballotdesign;

import civitas.common.ballot.Ballot;
import civitas.util.Use;

public class CheckBallotAgainstBallotDesign {

	@Use
	CalculateBallotLength calculateBallotLength;
	@Use
	CalculatePositionInBallot calculatePositionInBallot;
	@Use
	ConvertChoiceToString convertChoiceToString;

	public void apply(BallotDesign that, Ballot b)
			throws IllegalArgumentException {

		if (b.k != that.candidates.length
				|| b.matrix.length != calculateBallotLength.apply(b.k)) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}

		for (int i = 0; i < b.k; i++) {
			for (int j = i + 1; j < b.k; j++) {
				int choice = b.matrix[calculatePositionInBallot.apply(i, j, b.k)];
				if ("INVALID".equals(convertChoiceToString.apply(choice))) {
					throw new IllegalArgumentException(
							"Illegal choice for (" + i + "," + j + ")");
				}

			}
		}
	}

}
