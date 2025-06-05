package civitas.crypto.proofvote;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VerifyProofVoteStub implements ProofVoteTestData {
	public static VerifyProofVote stub() {

		VerifyProofVote mock = mock(VerifyProofVote.class);
		when(mock.apply(PROOF_VOTE, EL_GAMAL_PARAMETERS, CIPHERTEXT_ENCCAP,
				REENCRYPTED_WELL_KNOWN_CHOICE, CONTEXT_0)).thenReturn(true);
		return mock;
	}
}
