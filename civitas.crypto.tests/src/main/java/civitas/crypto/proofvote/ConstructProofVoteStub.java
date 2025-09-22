package civitas.crypto.proofvote;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConstructProofVoteStub implements ProofVoteTestData {
	public static ConstructProofVote stub() {
		ConstructProofVote mock = mock(ConstructProofVote.class);

		for (Integer piece : VOTE_PIECES) {
			when(mock.apply(eq(EL_GAMAL_PARAMETERS), any(), any(), eq(CONTEXT_MAP.get(piece)), any(), any()))
					.thenReturn(PROOF_VOTE_MAP.get(piece));
		}

		return mock;
	}
}
