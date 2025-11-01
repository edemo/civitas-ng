package civitas.crypto.parameters.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.Constants;
import civitas.crypto.parameters.GenerateSchnorrPrime;
import civitas.crypto.parameters.PrimePair;
import civitas.crypto.privatekey.tests.ElGamalPrivateKeyTestData;

class GenerateSchnorrPrimeTest extends RandomAwareTestBase implements ElGamalPrivateKeyTestData, Constants {

	@InjectMocks
	GenerateSchnorrPrime generateSchnorrPrime;

	@Test
	@DisplayName("schnorrPrime returns two primes p,q where p=q*r+1, and "
			+ "length of q is qLength, length of p is pLength ")
	void schnorrPrimeTest2() {
		PrimePair sp = generateSchnorrPrime.apply(EL_GAMAL_KEY_LENGTH, EL_GAMAL_GROUP_LENGTH);
		assertEquals(BIGINT_Q, sp.q);
		assertEquals(BIGINT_P, sp.p);
	}
}
