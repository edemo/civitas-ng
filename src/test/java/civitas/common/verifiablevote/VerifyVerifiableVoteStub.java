package civitas.common.verifiablevote;

import civitas.util.DI;

public class VerifyVerifiableVoteStub {

	public static VerifyVerifiableVote stub() {
		return DI.get(VerifyVerifiableVote.class);
	}
}
