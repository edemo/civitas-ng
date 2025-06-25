package civitas.crypto.rsapublickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreatePublicKeyFromWireStub implements PublicKeyTestData {
	public static CreatePublicKeyFromWire stub() {
		CreatePublicKeyFromWire mock = mock(CreatePublicKeyFromWire.class);
		when(mock.apply(PUBLIC_KEY_ON_WIRE)).thenReturn(PUBLIC_KEY);
		return mock;
	}
}
