package civitas.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import civitas.crypto.rsapublickey.PublicKeyTestData;

class GetServerPublicKeyStub implements PublicKeyTestData {
	public static GetServerPublicKey stub()
			throws IllegalArgumentException, InvalidKeySpecException, IOException {
		GetServerPublicKey mock = mock(GetServerPublicKey.class);
		when(mock.apply()).thenReturn(PUBLIC_KEY_ON_WIRE);
		return mock;
	}
}
