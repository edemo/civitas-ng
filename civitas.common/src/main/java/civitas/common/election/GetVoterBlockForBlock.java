package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.ballotdesign.CalculateBallotLength;

@Controller
public class GetVoterBlockForBlock {

	@Autowired
	CalculateBallotLength calculateBallotLength;

	public int apply(ElectionDetails that, int block) {
		int numberContexts = calculateBallotLength.apply(that.ballotDesign.getCandidates().length);
		return block / numberContexts;
	}
}
