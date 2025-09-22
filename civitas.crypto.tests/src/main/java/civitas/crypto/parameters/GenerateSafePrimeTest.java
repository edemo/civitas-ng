package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.Constants;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;

class GenerateSafePrimeTest extends RandomAwareTestBase
		implements Constants, ElGamalParametersTestData, PrivateKeyTestData {

	@InjectMocks
	GenerateSafePrime generateSafePrime;

	@Test
	@DisplayName("safePrime returns two primes p,q where p=2*q+1, and length of q is length ")
	void safePrimeTest() {
		PrimePair sp = generateSafePrime.apply(SAFE_KEY_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(sp.p, sp.q.multiply(TWO).add(ONE));
		assertEquals(SAFE_KEY_LENGTH, sp.q.bitLength());
	}
}
