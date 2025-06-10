package civitas.common.mix.voteelementrevelation;

import static org.mockito.Mockito.mock;

import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;

public interface MixVoteElementRevelationTestData
		extends ElGamalReencryptFactorTestData {

	MixVoteElementRevelation VOTE_ELEMENT_REVELATION_MOCK = mock(
			MixVoteElementRevelation.class);
}
