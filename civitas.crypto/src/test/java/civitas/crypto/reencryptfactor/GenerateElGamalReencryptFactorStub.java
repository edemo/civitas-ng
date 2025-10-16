package civitas.crypto.reencryptfactor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateElGamalReencryptFactorStub implements ElGamalReencryptFactorTestData {

	public static GenerateElGamalReencryptFactor stub() {
		GenerateElGamalReencryptFactor mock = mock(GenerateElGamalReencryptFactor.class);
		when(mock.apply(EL_GAMAL_PARAMETERS))
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_EPRIME)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_E)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_EPRIME)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_E)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_EPRIME)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_E)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_EPRIME)
				.thenReturn(ELGAMAL_REENCRYPT_FACTOR_E);
		return mock;
	}
}
