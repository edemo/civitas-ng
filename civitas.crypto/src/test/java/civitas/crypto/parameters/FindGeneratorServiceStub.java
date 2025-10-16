package civitas.crypto.parameters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindGeneratorServiceStub implements ElGamalParametersTestData {

	public static FindGeneratorService stub() {
		FindGeneratorService findGenerator = mock(FindGeneratorService.class);
		when(findGenerator.apply(SCHNORR_PRIMES)).thenReturn(BIGINT_G);
		when(findGenerator.apply(SAFE_PRIMES)).thenReturn(SAFE_G);
		return findGenerator;
	}
}
