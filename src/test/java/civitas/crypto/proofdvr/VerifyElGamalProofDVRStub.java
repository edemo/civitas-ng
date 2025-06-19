package civitas.crypto.proofdvr;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerifyElGamalProofDVRStub implements ElGamalProofDVRTestData {
	public static VerifyElGamalProofDVR stub() {
		VerifyElGamalProofDVR mock = mock(VerifyElGamalProofDVR.class);
		for (ElGamalProofDVR element : PROOFS)
			when(mock.apply(element, EL_GAMAL_PUBLIC_KEY_E,
					EL_GAMAL_PUBLIC_KEY_E)).thenReturn(true);
		return mock;
	}
}
