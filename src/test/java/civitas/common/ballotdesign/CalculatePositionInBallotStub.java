package civitas.common.ballotdesign;

import civitas.util.DI;

public class CalculatePositionInBallotStub {
	public static CalculatePositionInBallot stub() {
		return DI.get(CalculatePositionInBallot.class);
	}
}
