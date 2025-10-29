package civitas.common.verifiablevote.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifyVerifiableVote;

public class VerifyVerifiableVoteStub implements VerifiableVoteTestData {

	public static VerifyVerifiableVote stub() {
		VerifyVerifiableVote mock = mock(VerifyVerifiableVote.class);
		for (VerifiableVote element : VERIFIABLE_VOTES) {
			when(mock.apply(element, EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, CIPHERTEXT_LIST.size()))
					.thenReturn(true);
		}
		return mock;
	}
}
