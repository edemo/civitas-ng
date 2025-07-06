package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.BasicValuesTestData;

public class GenerateRandomIntStub implements BasicValuesTestData {

	public static GenerateRandomInt stub() {
		GenerateRandomInt mock = mock(GenerateRandomInt.class);
		when(mock.apply(5)).thenReturn(3).thenReturn(2).thenReturn(1).thenReturn(4)
				.thenReturn(0);
		return mock;
	}

}
