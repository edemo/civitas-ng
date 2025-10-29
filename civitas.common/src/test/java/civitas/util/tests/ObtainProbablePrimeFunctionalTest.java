package civitas.util.tests;

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
import civitas.crypto.CryptoBase;
import civitas.crypto.parameters.tests.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class ObtainProbablePrimeFunctionalTest extends RandomAwareTestBase implements ElGamalParametersTestData {

	@Autowired
	CryptoBase cryptoBase;

	@Test
	@DisplayName("obtain a probable prime with the given bit length, certainty and random generator")
	void test() {
		CivitasBigInteger p = cryptoBase.obtainProbablePrime(EL_GAMAL_KEY_LENGTH);
		assertTrue(p.isProbablePrime(CERTAINTY));
		assertEquals(EL_GAMAL_KEY_LENGTH, p.bitLength());
	}
}
