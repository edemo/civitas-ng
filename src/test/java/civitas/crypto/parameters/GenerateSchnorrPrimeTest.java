package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;
import civitas.util.Tested;

public class GenerateSchnorrPrimeTest extends TestBase
		implements ElGamalPrivateKeyTestData, Constants {

	@Tested
	GenerateSchnorrPrime generateSchnorrPrime;

	@Test
	@DisplayName("schnorrPrime returns two primes p,q where p=q*r+1, and "
			+ "length of q is qLength, length of p is pLength ")
	void schnorrPrimeTest2() {
		PrimePair sp = generateSchnorrPrime.apply(Constants.EL_GAMAL_KEY_LENGTH,
				Constants.EL_GAMAL_GROUP_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(ZERO, sp.p.subtract(ONE).mod(sp.q));
		assertEquals(Constants.EL_GAMAL_GROUP_LENGTH, sp.p.bitLength());
		assertEquals(Constants.EL_GAMAL_KEY_LENGTH, sp.q.bitLength());
	}

}
