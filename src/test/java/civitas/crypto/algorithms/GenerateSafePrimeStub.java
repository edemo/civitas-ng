package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.parameters.ElGamalParametersCTestData;

public class GenerateSafePrimeStub implements ElGamalParametersCTestData {

	public static GenerateSafePrime stub() {
		GenerateSafePrime generateSafePrime = mock(GenerateSafePrime.class);
		when(generateSafePrime.apply(SAFE_KEY_LENGTH)).thenReturn(SAFE_PRIMES);

		return generateSafePrime;
	}
}
