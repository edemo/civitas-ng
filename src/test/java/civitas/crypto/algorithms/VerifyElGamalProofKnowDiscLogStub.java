package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.ElGamalKeyShareTestData;

public class VerifyElGamalProofKnowDiscLogStub
		implements ElGamalKeyShareTestData {

	public static VerifyElGamalProofKnowDiscLog stub() {
		VerifyElGamalProofKnowDiscLog stub = mock(
				VerifyElGamalProofKnowDiscLog.class);
		when(stub.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG, EL_GAMAL_PARAMETERS))
				.thenReturn(true);
		return stub;
	}
}
