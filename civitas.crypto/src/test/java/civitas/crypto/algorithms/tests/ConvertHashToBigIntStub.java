package civitas.crypto.algorithms.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.proof1ofl.tests.ElGamalProof1OfLTestData;
import civitas.crypto.proofdisclog.tests.ElGamalProofDiscLogEqualityTestData;
import civitas.crypto.proofdvr.tests.ElGamalProofDVRTestData;
import civitas.crypto.proofvote.tests.ProofVoteTestData;

public class ConvertHashToBigIntStub
		implements ElGamalProof1OfLTestData,
				ElGamalProofDiscLogEqualityTestData,
				ProofVoteTestData,
				ElGamalProofDVRTestData {

	public static ConvertHashToBigInt stub() {
		ConvertHashToBigInt mock = mock(ConvertHashToBigInt.class);
		when(mock.apply(EL_GAMAL_PROOF_1_OF_L_HASH.toByteArray())).thenReturn(EL_GAMAL_PROOF_1_OF_L_HASH);
		when(mock.apply(PROOF_VOTE_C.toByteArray())).thenReturn(PROOF_VOTE_C);
		when(mock.apply(EL_GAMAL_PROOF_DVR_HASH)).thenReturn(DVR_HASH);
		when(mock.apply(FAKE_PROOF_DVR_HASH)).thenReturn(FAKE_PROOF_DVR_CT);
		when(mock.apply(SOMESTRING_HASH)).thenReturn(BIGINT_A);
		when(mock.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C.toByteArray()))
				.thenReturn(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C);

		return mock;
	}
}
