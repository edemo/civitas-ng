package civitas.crypto.parameters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateSafePrimeStub implements ElGamalParametersTestData {

	public static GenerateSafePrime stub() {
		GenerateSafePrime generateSafePrime = mock(GenerateSafePrime.class);
		when(generateSafePrime.apply(SAFE_KEY_LENGTH)).thenReturn(SAFE_PRIMES);

		return generateSafePrime;
	}
}
