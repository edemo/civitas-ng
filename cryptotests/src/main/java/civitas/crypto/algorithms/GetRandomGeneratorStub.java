package civitas.crypto.algorithms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import civitas.common.RandomFillerAnswer;

public class GetRandomGeneratorStub {

	public static GetRandomGenerator stub() {
		GetRandomGenerator mock = mock(GetRandomGenerator.class);
		Random randomMock = mock(Random.class);
		doAnswer(new RandomFillerAnswer()).when(randomMock).nextBytes(any());
		when(mock.apply()).thenReturn(randomMock);
		return mock;
	}

}
