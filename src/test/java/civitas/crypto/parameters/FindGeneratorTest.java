package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class FindGeneratorTest extends TestBase
		implements ElGamalParametersTestData {

	@InjectMocks
	FindGenerator findGenerator;

	@Test
	@DisplayName("finds the generator for a prime pair\n"
			+ "Implementation of step 3 of Algorithm 11.54 from Handbook of Applied Cryptography\n"
			+ "x is a random < p, r = (p-1)/q, g = x^r % p\n"
			+ "if g is neither 1 nor -1 mod p then it is a generator\n"
			+ "FIXME: can be a case when g = -1 mod p?")
	void test() {
		assertEquals(D_EXP_TWOK_FROMP,
				findGenerator.apply(new PrimePair(BIGINT_P, BIGINT_Q)));
	}

}
