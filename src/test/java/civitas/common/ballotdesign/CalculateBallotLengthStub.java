package civitas.common.ballotdesign;

import civitas.util.DI;

public class CalculateBallotLengthStub {
	public static CalculateBallotLength stub() {
		return DI.get(CalculateBallotLength.class);
	}

}
