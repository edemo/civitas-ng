package civitas.crypto.proofdisclog.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;

public class VerifyElGamalProofDiscLogEqualityStub implements ElGamalProofDiscLogEqualityTestData {
	public static VerifyElGamalProofDiscLogEquality stub() {
		VerifyElGamalProofDiscLogEquality mock = mock(VerifyElGamalProofDiscLogEquality.class);
		when(mock.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT, EL_GAMAL_PARAMETERS))
				.thenReturn(true);
		when(mock.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE, EL_GAMAL_PARAMETERS))
				.thenReturn(true);
		return mock;
	}
}
