package civitas.common.ballot;

import civitas.DI;

public class CreateEmptyBallotStub {
	public static CreateEmptyBallot stub() {
		return DI.get(CreateEmptyBallot.class);
	}
}
