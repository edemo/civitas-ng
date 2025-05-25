package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.crypto.SchnorrPrime;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalPrivateKeyCTestData;
import civitas.util.Use;

public class GenerateSchnorrPrimeTest extends ConcreteTestBase
		implements ElGamalPrivateKeyCTestData, Constants {

	@Use
	GenerateSchnorrPrime generateSchnorrPrime;

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

}
