package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.util.Tested;

public class LegendreSymbolTest extends ConcreteTestBase
		implements Constants, ElGamalParametersCTestData {

	@Tested
	LegendreSymbol legendreSymbol;

	@Test
	@DisplayName("legendreSymbol returns 1 if a is quadratic residue mod p"
			+ "The implementation uses Euler's criterion: "
			+ "legendre(a,p) = a^((p-1)/2) (mod p) "
			+ "NOTE: assumes p=2*q+1; use only with safe primes ")
	void legendreSymbolTest() {
		assertEquals(1, legendreSymbol.apply(BIGINT_A.add(ONE),
				EL_GAMAL_PARAMETERS_SAFE.p, EL_GAMAL_PARAMETERS_SAFE.q));
	}

	@Test
	@DisplayName("legendreSymbol returns -1 if a is not quadratic residue mod p")
	void legendreSymbolTest1() {
		assertEquals(-1, legendreSymbol.apply(BIGINT_A, EL_GAMAL_PARAMETERS_SAFE.p,
				EL_GAMAL_PARAMETERS_SAFE.q));
	}

	@Test
	@DisplayName("legendreSymbol returns 0 for zero")
	void legendreSymbolTest2() {
		assertEquals(0, legendreSymbol.apply(ZERO, EL_GAMAL_PARAMETERS_SAFE.p,
				EL_GAMAL_PARAMETERS_SAFE.q));
	}

	@Test
	@DisplayName("legendreSymbol may throw CryptoError if p != 2q+1")
	void legendreSymbolTest3() {
		assertThrows(CryptoError.class, () -> assertEquals(0,
				legendreSymbol.apply(BIGINT_A, BIGINT_P, BIGINT_Q)));
	}

}
