package civitas.crypto.parameters.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.Constants;
import civitas.crypto.parameters.CalculateNumberOfPrimeTests;

public class CalculateNumberOfPrimeTestsStub {

	public static CalculateNumberOfPrimeTests stub() {
		CalculateNumberOfPrimeTests mock = mock(CalculateNumberOfPrimeTests.class);
		when(mock.apply(Constants.EL_GAMAL_GROUP_LENGTH)).thenReturn(2);
		when(mock.apply(Constants.EL_GAMAL_KEY_LENGTH)).thenReturn(2);
		return mock;
	}
}
