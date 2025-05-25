package civitas.crypto.algorithms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.RandomAnswer;

public class GenerateRandomElementStub {

	public static GenerateRandomElement stub() {
		GenerateRandomElement mock = mock(GenerateRandomElement.class);
		when(mock.apply(any())).thenAnswer(new RandomAnswer());
		return mock;
	}

}
