package civitas.crypto.parameters.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.Constants;
import civitas.crypto.parameters.CalculateNumberOfPrimeTests;
import io.github.magwas.konveyor.testing.TestBase;

class CalculateNumberOfPrimeTestsTest extends TestBase {

	@InjectMocks
	CalculateNumberOfPrimeTests calculateNumberOfPrimeTests;

	@Test
	void test() {
		assertEquals(1024, calculateNumberOfPrimeTests.apply(Constants.EL_GAMAL_KEY_LENGTH));
	}
}
