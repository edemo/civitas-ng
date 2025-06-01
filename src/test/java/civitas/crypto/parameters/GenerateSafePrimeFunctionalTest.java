package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.util.Use;

@Tag("functional")
public class GenerateSafePrimeFunctionalTest extends TestBase
		implements Constants, ElGamalParametersTestData, PrivateKeyTestData {

	@Use
	GenerateSafePrime generateSafePrimeReal;
	@Use
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
