package civitas.common.tallystate;

import civitas.common.ballotdesign.BallotDesign;

public class CreateTallyState {

	public TallyState newTallyState(final BallotDesign that) {
		Integer size = that.getCandidates().length;
		Integer[][] matrix = new Integer[size][size];
		return new TallyState(size, matrix);
	}
}
