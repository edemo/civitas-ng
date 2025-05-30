package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.parameters.ElGamalParametersCTestData;

public class GenerateSchnorrPrimeStub implements ElGamalParametersCTestData {

	public static GenerateSchnorrPrime stub() {
		GenerateSchnorrPrime generateSchnorrPrime = mock(
				GenerateSchnorrPrime.class);
		when(generateSchnorrPrime.apply(Constants.EL_GAMAL_KEY_LENGTH,
				Constants.EL_GAMAL_GROUP_LENGTH)).thenReturn(SCHNORR_PRIMES);
		return generateSchnorrPrime;
	}
}
