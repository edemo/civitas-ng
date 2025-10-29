package civitas.common.mix.voteelementrevelation.tests;

import static org.mockito.Mockito.mock;

import civitas.common.mix.voteelementrevelation.MixVoteElementRevelation;
import civitas.crypto.reencryptfactor.tests.ElGamalReencryptFactorTestData;

public interface MixVoteElementRevelationTestData extends ElGamalReencryptFactorTestData {

	MixVoteElementRevelation VOTE_ELEMENT_REVELATION_MOCK = mock(MixVoteElementRevelation.class);

	MixVoteElementRevelation VOTE_ELEMENT_REVELATION = new MixVoteElementRevelation(
			ELGAMAL_REENCRYPT_FACTOR_E, ELGAMAL_REENCRYPT_FACTOR_EPRIME, 0, "nonce".getBytes());
}
