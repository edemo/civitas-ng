package civitas.crypto.proofdisclog.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;

public class ConstructElGamalDiscLogEqualityProofStub implements ElGamalProofDiscLogEqualityTestData {

	public static ConstructElGamalDiscLogEqualityProof stub() {
		ConstructElGamalDiscLogEqualityProof mock = mock(ConstructElGamalDiscLogEqualityProof.class);
		when(mock.apply(
						EL_GAMAL_PARAMETERS,
						CIPHERTEXT_E_A.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P),
						CIPHERTEXT_E_B.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P),
						FACTOR_E))
				.thenReturn(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

		when(mock.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E.a, BIGINT_G, EL_GAMAL_PRIVATE_KEY_E.x()))
				.thenReturn(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);
		return mock;
	}
}
