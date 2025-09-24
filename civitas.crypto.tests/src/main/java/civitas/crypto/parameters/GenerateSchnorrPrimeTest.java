package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.Constants;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;

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
