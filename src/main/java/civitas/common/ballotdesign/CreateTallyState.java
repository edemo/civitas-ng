package civitas.common.ballotdesign;

import civitas.common.CondorcetTallyState;
import civitas.common.TallyState;

public class CreateTallyState {

	public TallyState newTallyState(BallotDesign that) {
		return new CondorcetTallyState(
				that.candidates == null ? 0 : that.candidates.length);
	}

}
