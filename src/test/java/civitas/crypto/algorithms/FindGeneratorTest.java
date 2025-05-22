package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.SchnorrPrime;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public class FindGeneratorTest extends ConcreteTestBase {

	FindGenerator findGenerator = DI.get(FindGenerator.class);

	@Test
	@DisplayName("generator with value 1 is rejected")
	void test() {

		findGenerator.generateRandomElement = mock(GenerateRandomElement.class);
		when(findGenerator.generateRandomElement.apply(any()))
				.thenReturn(CivitasBigInteger.ONE).thenReturn(BIGINT_D);

		assertEquals(D_EXP_TWOK_FROMP,
				findGenerator.apply(new SchnorrPrime(BIGINT_P, BIGINT_Q)));
	}

	@Test
	@DisplayName("generator with value -1 is rejected")
	void test1() {

		findGenerator.generateRandomElement = mock(GenerateRandomElement.class);
		when(findGenerator.generateRandomElement.apply(any()))
				.thenReturn(BIGINT_P.subtract(CivitasBigInteger.ONE))
				.thenReturn(BIGINT_D);

		assertEquals(D_EXP_TWOK_FROMP,
				findGenerator.apply(new SchnorrPrime(BIGINT_P, BIGINT_Q)));
	}
}
