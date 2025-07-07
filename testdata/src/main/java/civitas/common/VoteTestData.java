package civitas.common;

import static org.mockito.Mockito.mock;

import civitas.comon.VoteChoiceTestData;

public interface VoteTestData extends VoteChoiceTestData {

	EncryptedVote VOTE_MOCK = mock(EncryptedVote.class);

	public static final EncryptedVote TO_VOTE_BAD_CAP = new EncryptedVote(CONTEXT_0,
			REENCRYPTED_CHOICE_MAP.get(VoteChoice.I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(1));

	public static final EncryptedVote TO_VOTE = new EncryptedVote(CONTEXT_0,
			REENCRYPTED_CHOICE_MAP.get(VoteChoice.I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(0));

	public static final EncryptedVote FROM_VOTE = new EncryptedVote(CONTEXT_0,
			CIPHERTEXT_LIST.get(VoteChoice.I_BEATS_J.ordinal()),
			ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(0));

}
