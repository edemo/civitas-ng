package civitas.common.ballot;

import civitas.util.DI;

public class RecordOnePairInBallotStub {
	public static RecordOnePairInBallot stub() {
		return DI.get(RecordOnePairInBallot.class);
	}
}
