package civitas.common;

import static org.mockito.Mockito.mock;

import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;

public interface VoteTestData
		extends ElGamalCiphertextTestData, ElGamal1OfLReencryptionTestData {

	Vote VOTE_MOCK = mock(Vote.class);

	public static final Vote TO_VOTE_BAD_CAP = new Vote(CONTEXT_0,
			REENCRYPTED_CHOICE_MAP.get(CommonConstants.VOTE_CHOICE_I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(1));

	public static final Vote TO_VOTE = new Vote(CONTEXT_0,
			REENCRYPTED_CHOICE_MAP.get(CommonConstants.VOTE_CHOICE_I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(0));

	public static final Vote FROM_VOTE = new Vote(CONTEXT_0,
			CIPHERTEXT_LIST.get(CommonConstants.VOTE_CHOICE_I_BEATS_J),
			ENCRYPTED_VOTE_CAPABILITIES.get(0));

}
