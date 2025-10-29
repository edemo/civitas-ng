package civitas.crypto.algorithms.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.algorithms.GenerateRandomInt;
import civitas.util.tests.BasicValuesTestData;

public class GenerateRandomIntStub implements BasicValuesTestData {

	public static GenerateRandomInt stub() {
		GenerateRandomInt mock = mock(GenerateRandomInt.class);
		when(mock.apply(5))
				.thenReturn(3)
				.thenReturn(2)
				.thenReturn(1)
				.thenReturn(4)
				.thenReturn(0);
		return mock;
	}
}
