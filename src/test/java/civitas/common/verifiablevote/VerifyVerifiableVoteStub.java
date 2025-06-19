package civitas.common.verifiablevote;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VerifyVerifiableVoteStub implements VerifiableVoteTestData {

	public static VerifyVerifiableVote stub() {
		VerifyVerifiableVote mock = mock(VerifyVerifiableVote.class);
		for (int i = 0; i < VERIFIABLE_VOTES.length; i++)
			when(mock.apply(VERIFIABLE_VOTES[i], EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, CIPHERTEXT_LIST.size())).thenReturn(true);
		return mock;
	}
}
