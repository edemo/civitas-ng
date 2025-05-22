package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindGeneratorStub implements GenerateElGamalParametersTestData {

	public static FindGenerator stub() {
		FindGenerator findGenerator = mock(FindGenerator.class);
		when(findGenerator.apply(SCHNORR_PRIMES)).thenReturn(BIGINT_G);
		when(findGenerator.apply(SAFE_PRIMES)).thenReturn(SAFE_G);
		return findGenerator;

	}

}
