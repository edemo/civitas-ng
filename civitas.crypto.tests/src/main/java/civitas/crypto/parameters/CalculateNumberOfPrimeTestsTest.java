package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.Constants;
import io.github.magwas.testing.TestBase;

class CalculateNumberOfPrimeTestsTest extends TestBase {

	@InjectMocks
	CalculateNumberOfPrimeTests calculateNumberOfPrimeTests;

	@Test
	void test() {
		assertEquals(1024,
				calculateNumberOfPrimeTests.apply(Constants.EL_GAMAL_KEY_LENGTH));
	}

}
