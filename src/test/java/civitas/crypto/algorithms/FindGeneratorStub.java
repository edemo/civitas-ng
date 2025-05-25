package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.ElGamalParametersCTestData;

public class FindGeneratorStub implements ElGamalParametersCTestData {

	public static FindGenerator stub() {
		FindGenerator findGenerator = mock(FindGenerator.class);
		when(findGenerator.apply(SCHNORR_PRIMES)).thenReturn(BIGINT_G);
		when(findGenerator.apply(SAFE_PRIMES)).thenReturn(SAFE_G);
		return findGenerator;

	}

}
