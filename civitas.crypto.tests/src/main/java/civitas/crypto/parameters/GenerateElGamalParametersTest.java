package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.Constants;
import io.github.magwas.testing.TestBase;

public class GenerateElGamalParametersTest extends TestBase
		implements ElGamalParametersTestData {

	@InjectMocks
	GenerateElGamalParameters generateElGamalParameters;

	@Test
	@DisplayName("makes a safe prime pair if groupLength == keyLength + 1")
	void test_1() {

		assertEquals(SAFE_G, generateElGamalParameters.apply(SAFE_KEY_LENGTH,
				SAFE_KEY_LENGTH + 1).g);
	}

	@Test
	@DisplayName("makes a Schnorr prime pair if groupLength != keyLength + 1")
	void test_2() {
		assertEquals(BIGINT_G,
				generateElGamalParameters.apply(Constants.EL_GAMAL_KEY_LENGTH,
						Constants.EL_GAMAL_GROUP_LENGTH).g);
	}

	@Test
	@DisplayName("without argument makes a prime pair with the builtin lengths:\n"
			+ "Constants.EL_GAMAL_KEY_LENGTH and\n"
			+ "Constants.EL_GAMAL_GROUP_LENGTH")
	void test_3() {
		assertEquals(BIGINT_G, generateElGamalParameters.apply().g);
	}

}
