package civitas.crypto.reencryptfactor.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;

class GenerateElGamalReencryptFactorTest extends RandomAwareTestBase implements ElGamalReencryptFactorTestData {

	@InjectMocks
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	@Test
	void test() {
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_RANDOMS_0, generateElGamalReencryptFactor.apply(EL_GAMAL_PARAMETERS));
	}
}
