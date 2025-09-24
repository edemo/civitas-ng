package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.ballotdesign.CalculateBallotLength;

@Controller
public class GetContextForBlock {
	@Autowired
	CalculateBallotLength calculateBallotLength;

	public int apply(final ElectionDetails that, final int block) {
		int numberContexts = calculateBallotLength.apply(that.ballotDesign.getCandidates().length);
		return block % numberContexts;
	}
}
