package civitas.crypto.algorithms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.ElGamalProofDiscLogEqualityCTestData;

public class ConstructElGamalDiscLogEqualityProofStub
		implements ElGamalProofDiscLogEqualityCTestData {

	public static ConstructElGamalDiscLogEqualityProof stub() {
		ConstructElGamalDiscLogEqualityProof mock = mock(
				ConstructElGamalDiscLogEqualityProof.class);
		when(mock.apply(any(), any(), any(), any()))
				.thenReturn(EL_GAMAL_DISC_LOG_EQUALITY_PROOF);
		return mock;

	}
}
