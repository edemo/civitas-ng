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
import civitas.crypto.Constants;
import civitas.crypto.parameters.GenerateElGamalParameters;
import civitas.crypto.parameters.GenerateSafePrime;
import civitas.crypto.parameters.PrimePair;
import civitas.crypto.parameters.tests.ElGamalParametersTestData;
import civitas.crypto.rsaprivatekey.tests.PrivateKeyTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class GenerateSafePrimeFunctionalTest implements Constants, ElGamalParametersTestData, PrivateKeyTestData {

	@Autowired
	GenerateSafePrime generateSafePrimeReal;

	@Autowired
	GenerateElGamalParameters generateElGamalParameters;

	@Test
	@DisplayName("running it with real random generator"
			+ "FIXME: uses random, should make a multirun verification test but it takes ages")
	void safePrimeTestReal() {
		PrimePair sp = generateSafePrimeReal.apply(SAFE_KEY_LENGTH);
		assertTrue(sp.p.isProbablePrime(CERTAINTY));
		assertTrue(sp.q.isProbablePrime(CERTAINTY));
		assertEquals(sp.p, sp.q.multiply(TWO).add(ONE));
		assertEquals(SAFE_KEY_LENGTH, sp.q.bitLength());
	}
}
