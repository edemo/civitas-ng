package civitas.crypto.sharedkey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.crypto.KeyGenerator;

class GetSharedKeyGeneratorStub implements SharedKeyTestData {
	public static GetSharedKeyGenerator stub() {
		GetSharedKeyGenerator mock = mock(GetSharedKeyGenerator.class);
		KeyGenerator generator = mock(KeyGenerator.class);
		when(generator.generateKey()).thenReturn(SHARED_KEY_SPEC);
		when(mock.apply(SHARED_KEY_SIZE)).thenReturn(generator);
		return mock;
	}
}
