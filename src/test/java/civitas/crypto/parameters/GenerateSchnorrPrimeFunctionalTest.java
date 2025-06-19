package civitas.crypto.parameters;

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
import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class GenerateSchnorrPrimeFunctionalTest extends TestBase
		implements ElGamalPrivateKeyTestData, Constants {

	@Autowired
	GenerateSchnorrPrime generateSchnorrPrimeReal;

	@Test
	@DisplayName("making sure that we get a correct pair using the real random generator"
			+ "FIXME: uses random, should make a multirun verification test, but it takes a lot of time")
	void schnorrPrimeTest() {
		PrimePair sp = generateSchnorrPrimeReal.apply(Constants.EL_GAMAL_KEY_LENGTH,
				Constants.EL_GAMAL_GROUP_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(ZERO, sp.p.subtract(ONE).mod(sp.q));
		assertEquals(Constants.EL_GAMAL_GROUP_LENGTH, sp.p.bitLength());
		assertEquals(Constants.EL_GAMAL_KEY_LENGTH, sp.q.bitLength());
	}

}
