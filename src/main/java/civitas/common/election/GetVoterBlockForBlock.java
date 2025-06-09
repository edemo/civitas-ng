package civitas.common.election;

import civitas.common.ballotdesign.CalculateBallotLength;
import civitas.util.Use;

public class GetVoterBlockForBlock {

	@Use
	CalculateBallotLength calculateBallotLength;

	public int apply(ElectionDetails that, int block) {
		int numberContexts = calculateBallotLength
				.apply(that.ballotDesign.candidates.length);
		return block / numberContexts;
	}
}
