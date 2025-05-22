package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.util.Tested;

public class GenerateElGamalParametersTest extends ConcreteTestBase
		implements GenerateElGamalParametersTestData {

	@Tested
	GenerateElGamalParameters generateElGamalParameters = new GenerateElGamalParameters();

	@Test
	@DisplayName("makes a safe prime pair if groupLength == keyLength + 1")
	void test_1() {

		assertEquals(SAFE_G, generateElGamalParameters.apply(SAFE_KEY_LENGTH,
				CryptoFactoryC.EL_GAMAL_GROUP_LENGTH).g);
	}

	@Test
	@DisplayName("makes a Schnorr prime pair if groupLength != keyLength + 1")
	void test_2() {

		assertEquals(BIGINT_G,
				generateElGamalParameters.apply(CryptoFactoryC.EL_GAMAL_KEY_LENGTH,
						CryptoFactoryC.EL_GAMAL_GROUP_LENGTH).g);
	}

}
