package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.CryptoFactoryC;

public class GenerateSchnorrPrimeStub
		implements GenerateElGamalParametersTestData {

	public static GenerateSchnorrPrime stub() {
		GenerateSchnorrPrime generateSchnorrPrime = mock(
				GenerateSchnorrPrime.class);
		when(generateSchnorrPrime.apply(CryptoFactoryC.EL_GAMAL_KEY_LENGTH,
				CryptoFactoryC.EL_GAMAL_GROUP_LENGTH)).thenReturn(SCHNORR_PRIMES);
		return generateSchnorrPrime;
	}
}
