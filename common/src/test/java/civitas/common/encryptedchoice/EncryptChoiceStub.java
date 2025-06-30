package civitas.common.encryptedchoice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.votersubmission.VoterSubmissionTestData;

class EncryptChoiceStub implements VoterSubmissionTestData {

	public static EncryptChoice stub() {
		EncryptChoice mock = mock(EncryptChoice.class);
		for (Integer piece : VOTE_PIECES) {
			when(mock.apply(any(), any(), any(), eq(piece)))
					.thenReturn(new EncryptedChoice(ELGAMAL_REENCRYPT_FACTOR_EPRIME,
							EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(BALLOT.matrix[piece])));
		}
		return mock;
	}
}
