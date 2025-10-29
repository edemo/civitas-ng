package civitas.crypto.sharedkey.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Base64;

import civitas.crypto.sharedkey.CreateSharedKeyFromBytes;

public class CreateSharedKeyFromBytesStub implements SharedKeyTestData {

	public static CreateSharedKeyFromBytes stub() {
		CreateSharedKeyFromBytes mock = mock(CreateSharedKeyFromBytes.class);
		when(mock.apply(Base64.getDecoder().decode(SHARED_KEY_BASE64))).thenReturn(SHARED_KEY.k());
		return mock;
	}
}
