package civitas.common.verifiablevote;

import civitas.DI;

public class VerifyVerifiableVoteStub {

	public static VerifyVerifiableVote stub() {
		return DI.get(VerifyVerifiableVote.class);
	}
}
