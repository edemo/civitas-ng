package civitas.crypto.proofdvr.tests;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.proofdvr.VerifyElGamalProofDVR;

public class VerifyElGamalProofDVRStub implements ElGamalProofDVRTestData {
	public static VerifyElGamalProofDVR stub() {
		VerifyElGamalProofDVR mock = mock(VerifyElGamalProofDVR.class);
		for (ElGamalProofDVR element : PROOFS) {
			when(mock.apply(eq(element), eq(EL_GAMAL_PUBLIC_KEY_E), eq(EL_GAMAL_PUBLIC_KEY_E)))
					.thenReturn(true);
		}
		return mock;
	}
}
