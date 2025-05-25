package civitas.crypto.algorithms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.security.SecureRandom;
import java.util.Random;

import civitas.common.RandomFillerAnswer;

public class GetRandomGeneratorStub {

	Random stub() {
		SecureRandom mock = mock(SecureRandom.class,
				withSettings().withoutAnnotations());
		doAnswer(new RandomFillerAnswer()).when(mock).nextBytes(any());
		return mock;
	}

}
