package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.crypto.SchnorrPrime;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalPrivateKeyCTestData;
import civitas.util.Tested;

public class GenerateSafePrimeTest extends ConcreteTestBase
		implements Constants, ElGamalPrivateKeyCTestData {

	@Tested
	GenerateSafePrime generateSafePrime;

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

}
