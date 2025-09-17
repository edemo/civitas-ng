package civitas.common.mix.votemix;

import static org.mockito.Mockito.mock;

import civitas.common.EncryptedVote;
import civitas.common.VoteTestData;

public interface VoteMixTestData extends VoteTestData {

	VoteMix VOTE_MIX_MOCK = mock(VoteMix.class);

	VoteMix FROM_MIX = new VoteMix(5, "".getBytes(), new byte[][] {},
			new EncryptedVote[] { FROM_VOTE });
	VoteMix TO_MIX = new VoteMix(6, "".getBytes(), new byte[][] {},
			new EncryptedVote[] { TO_VOTE_BAD_CAP, TO_VOTE });

}
