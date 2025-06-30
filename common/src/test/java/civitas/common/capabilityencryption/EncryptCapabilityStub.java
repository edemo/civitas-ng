package civitas.common.capabilityencryption;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.votersubmission.VoterSubmissionTestData;

class EncryptCapabilityStub implements VoterSubmissionTestData {
	public static EncryptCapability stub() {
		EncryptCapability mock = mock(EncryptCapability.class);
		for (Integer piece : VOTE_PIECES) {
			when(mock.apply(any(), any(), eq(CONTEXT_MAP.get(piece))))
					.thenReturn(new CapabilityEncryption(ELGAMAL_REENCRYPT_FACTOR_E,
							ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(piece)));
		}
		return mock;
	}
}
