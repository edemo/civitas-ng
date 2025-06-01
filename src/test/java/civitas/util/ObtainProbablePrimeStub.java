package civitas.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.parameters.ElGamalParametersTestData;

public class ObtainProbablePrimeStub implements ElGamalParametersTestData {

	public static ObtainProbablePrime stub() {
		ObtainProbablePrime mock = mock(ObtainProbablePrime.class);
		when(mock.apply(EL_GAMAL_KEY_LENGTH, CERTAINTY, RANDOM))
				.thenReturn(BIGINT_B).thenReturn(BIGINT_NO_PRIME_OF_KEYLENGTH)
				.thenReturn(BIGINT_Q);
		when(mock.apply(SAFE_KEY_LENGTH, CERTAINTY, RANDOM)).thenReturn(BIGINT_B)
				.thenReturn(SAFE_Q);

		return mock;

	}

}
