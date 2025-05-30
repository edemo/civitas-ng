package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.ProofKnowDiscLogTestData;

public class ConstructProofKnowDiscLogStub implements ProofKnowDiscLogTestData {
	public static ConstructProofKnowDiscLog stub() {
		ConstructProofKnowDiscLog stub = mock(ConstructProofKnowDiscLog.class);
		when(stub.apply(EL_GAMAL_PARAMETERS, ELGAMAL_PRIVATE_KEY_E))
				.thenReturn(EL_GAMAL_PROOF_KNOWN_DISC_LOG);
		when(stub.apply(EL_GAMAL_PARAMETERS, ELGAMAL_PRIVATE_KEY_EPRIME))
				.thenReturn(EL_GAMAL_PROOF_KNOWN_DISC_LOG_BAD);
		return stub;
	}

}
