package civitas.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.parameters.ElGamalParametersTestData;

public class ObtainProbablePrimeStub implements ElGamalParametersTestData {

	public static ObtainProbablePrime stub() {
		ObtainProbablePrime mock = mock(ObtainProbablePrime.class);
		when(mock.apply(eq(EL_GAMAL_KEY_LENGTH), eq(CERTAINTY), any()))
				.thenReturn(BIGINT_B).thenReturn(BIGINT_NO_PRIME_OF_KEYLENGTH)
				.thenReturn(BIGINT_Q);
		when(mock.apply(eq(SAFE_KEY_LENGTH), eq(CERTAINTY), any()))
				.thenReturn(BIGINT_B).thenReturn(SAFE_Q);

		return mock;

	}

}
