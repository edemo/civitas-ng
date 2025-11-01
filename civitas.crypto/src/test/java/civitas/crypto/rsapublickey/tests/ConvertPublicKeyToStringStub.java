package civitas.crypto.rsapublickey.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.rsapublickey.ConvertPublicKeyToString;

public class ConvertPublicKeyToStringStub implements PublicKeyTestData {
	public static ConvertPublicKeyToString stub() {
		ConvertPublicKeyToString mock = mock(ConvertPublicKeyToString.class);
		when(mock.apply(PUBLIC_KEY)).thenReturn(PUBLIC_KEY_BASE64);
		return mock;
	}
}
