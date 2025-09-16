package civitas.common;

import static org.mockito.Mockito.mock;

public interface VoteTestData extends VoteChoiceTestData {

	EncryptedVote VOTE_MOCK = mock(EncryptedVote.class);

	EncryptedVote TO_VOTE_BAD_CAP = new EncryptedVote(
			CONTEXT_0, REENCRYPTED_CHOICE_MAP.get(VoteChoice.I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(1));

	EncryptedVote TO_VOTE = new EncryptedVote(CONTEXT_0,
			REENCRYPTED_CHOICE_MAP.get(VoteChoice.I_BEATS_J),
			REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(0));

	EncryptedVote FROM_VOTE = new EncryptedVote(CONTEXT_0,
			CIPHERTEXT_LIST.get(VoteChoice.I_BEATS_J.ordinal()),
			ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(0));

}
