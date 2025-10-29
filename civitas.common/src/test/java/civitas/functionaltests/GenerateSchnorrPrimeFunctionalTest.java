package civitas.functionaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.Constants;
import civitas.crypto.parameters.GenerateSchnorrPrime;
import civitas.crypto.parameters.PrimePair;
import civitas.crypto.privatekey.tests.ElGamalPrivateKeyTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class GenerateSchnorrPrimeFunctionalTest extends RandomAwareTestBase implements ElGamalPrivateKeyTestData, Constants {

	@Autowired
	GenerateSchnorrPrime generateSchnorrPrimeReal;

	@Test
	@DisplayName("making sure that we get a correct pair using the real random generator"
			+ "FIXME: uses random, should make a multirun verification test, but it takes a lot of time")
	void schnorrPrimeTest() {
		PrimePair sp = generateSchnorrPrimeReal.apply(EL_GAMAL_KEY_LENGTH, EL_GAMAL_GROUP_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(ZERO, sp.p.subtract(ONE).mod(sp.q));
		assertEquals(EL_GAMAL_GROUP_LENGTH, sp.p.bitLength());
		assertEquals(EL_GAMAL_KEY_LENGTH, sp.q.bitLength());
	}
}
