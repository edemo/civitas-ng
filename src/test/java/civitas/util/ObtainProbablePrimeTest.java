package civitas.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.parameters.ElGamalParametersTestData;

@Tag("functional")
public class ObtainProbablePrimeTest extends TestBase
		implements ElGamalParametersTestData {

	@Autowired
	ObtainProbablePrime obtainProbablePrime;

	@Test
	@DisplayName("obtain a probable prime with the given bit length, certainty and random generator")
	void test() {
		CivitasBigInteger p = obtainProbablePrime.apply(EL_GAMAL_KEY_LENGTH,
				Constants.CERTAINTY, RANDOM);
		assertTrue(p.isProbablePrime(CERTAINTY));
		assertEquals(EL_GAMAL_KEY_LENGTH, p.bitLength());
	}

}
