package civitas.crypto.proofvote.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.proofvote.VerifyProofVote;

public class VerifyProofVoteStub implements ProofVoteTestData {
	public static VerifyProofVote stub() {

		VerifyProofVote mock = mock(VerifyProofVote.class);
		when(mock.apply(PROOF_VOTE, EL_GAMAL_PARAMETERS, CIPHERTEXT_ENCCAP, REENCRYPTED_WELL_KNOWN_CHOICE, CONTEXT_0))
				.thenReturn(true);
		for (Integer piece : VOTE_PIECES) {
			when(mock.apply(
							eq(PROOF_VOTE_MAP.get(piece)),
							eq(EL_GAMAL_PARAMETERS),
							any(),
							any(),
							eq(CONTEXT_MAP.get(piece))))
					.thenReturn(true);
		}

		return mock;
	}
}
