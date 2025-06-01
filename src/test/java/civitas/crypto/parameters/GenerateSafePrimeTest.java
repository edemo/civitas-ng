package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.util.Tested;
import civitas.util.Use;

public class GenerateSafePrimeTest extends TestBase
		implements Constants, ElGamalParametersTestData, PrivateKeyTestData {

	@Tested
	GenerateSafePrime generateSafePrime;

	@Use
	GenerateElGamalParameters generateElGamalParameters;

	@Test
	@DisplayName("safePrime returns two primes p,q where p=2*q+1, and length of q is length ")
	void safePrimeTest() {
		int a = KEYSIZE;
		PrimePair sp = generateSafePrime.apply(SAFE_KEY_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(sp.p, sp.q.multiply(TWO).add(ONE));
		assertEquals(SAFE_KEY_LENGTH, sp.q.bitLength());
	}

}
