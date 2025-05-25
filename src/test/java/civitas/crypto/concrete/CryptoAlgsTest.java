package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoError;
import civitas.crypto.SchnorrPrime;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.GenerateSafePrime;
import civitas.crypto.algorithms.GenerateSchnorrPrime;
import civitas.crypto.algorithms.LegendreSymbol;
import civitas.util.DI;

public class CryptoAlgsTest extends ConcreteTestBase
		implements ConcreteTestData, Constants {

	GenerateSafePrime generateSafePrime = DI.get(GenerateSafePrime.class);
	GenerateSchnorrPrime generateSchnorrPrime = DI
			.get(GenerateSchnorrPrime.class);
	LegendreSymbol legendreSymbol = DI.get(LegendreSymbol.class);

	@Test
	@Tag("functional")
	@DisplayName("safePrime returns two primes p,q where p=2*q+1, and length of q is length "
			+ "FIXME: uses random, must make a multirun verification test")
	void safePrimeTest() {
		SchnorrPrime sp = generateSafePrime.apply(KEYSIZE);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(sp.p, sp.q.multiply(TWO).add(ONE));
		assertEquals(KEYSIZE, sp.q.bitLength());
	}

	@Test
	@Tag("functional")
	@DisplayName("schnorrPrime returns two primes p,q where p=q*r+1, and "
			+ "length of q is qLength, length of p is pLength "
			+ "FIXME: uses random, must make a multirun verification test")
	void schnorrPrimeTest() {
		SchnorrPrime sp = generateSchnorrPrime.apply(
				CryptoFactoryC.EL_GAMAL_KEY_LENGTH,
				CryptoFactoryC.EL_GAMAL_GROUP_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(ZERO, sp.p.subtract(ONE).mod(sp.q));
		assertEquals(CryptoFactoryC.EL_GAMAL_GROUP_LENGTH, sp.p.bitLength());
		assertEquals(CryptoFactoryC.EL_GAMAL_KEY_LENGTH, sp.q.bitLength());
	}

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
