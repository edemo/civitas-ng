package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.ballotdesign.CalculateBallotLength;

@Service
public class GetContextForBlock {
	@Autowired
	CalculateBallotLength calculateBallotLength;

	public int apply(ElectionDetails that, int block) {
		int numberContexts = calculateBallotLength
				.apply(that.ballotDesign.candidates.length);
		return block % numberContexts;

	}

}
