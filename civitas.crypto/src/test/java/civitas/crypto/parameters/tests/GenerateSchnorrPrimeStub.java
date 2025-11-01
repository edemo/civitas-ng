package civitas.crypto.parameters.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.parameters.GenerateSchnorrPrime;

public class GenerateSchnorrPrimeStub implements ElGamalParametersTestData {

	public static GenerateSchnorrPrime stub() {
		GenerateSchnorrPrime generateSchnorrPrime = mock(GenerateSchnorrPrime.class);
		when(generateSchnorrPrime.apply(EL_GAMAL_KEY_LENGTH, EL_GAMAL_GROUP_LENGTH))
				.thenReturn(SCHNORR_PRIMES);
		return generateSchnorrPrime;
	}
}
