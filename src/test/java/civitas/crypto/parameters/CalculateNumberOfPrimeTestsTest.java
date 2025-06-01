package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.util.Tested;

class CalculateNumberOfPrimeTestsTest extends TestBase {

	@Tested
	CalculateNumberOfPrimeTests calculateNumberOfPrimeTests;

	@Test
	void test() {
		assertEquals(1024,
				calculateNumberOfPrimeTests.apply(Constants.EL_GAMAL_KEY_LENGTH));
	}

}
