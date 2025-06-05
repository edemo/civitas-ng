package civitas.common.ballot;

import civitas.util.DI;

public class CreateEmptyBallotStub {
	public static CreateEmptyBallot stub() {
		return DI.get(CreateEmptyBallot.class);
	}
}
