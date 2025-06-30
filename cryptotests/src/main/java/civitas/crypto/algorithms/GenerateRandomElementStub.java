package civitas.crypto.algorithms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.RandomAnswer;
import civitas.crypto.parameters.ElGamalParametersTestData;

public class GenerateRandomElementStub implements ElGamalParametersTestData {

	public static GenerateRandomElement stub() {
		GenerateRandomElement mock = mock(GenerateRandomElement.class);
		when(mock.apply(any())).thenAnswer(new RandomAnswer());
		when(mock.apply(TWO_EXP_GROUP_LENGTH)).thenReturn(BIGINT_A)
				.thenReturn(BIGINT_NO_PRIME_OF_GROUPLENGTH).thenReturn(BIGINT_P);
		when(mock.apply(BIGINT_P)).thenReturn(ONE)
				.thenReturn(BIGINT_P.subtract(ONE)).thenReturn(BIGINT_D);

		return mock;
	}

}
