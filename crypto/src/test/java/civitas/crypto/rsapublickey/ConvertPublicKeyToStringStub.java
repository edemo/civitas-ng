package civitas.crypto.rsapublickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConvertPublicKeyToStringStub implements PublicKeyTestData {
	public static ConvertPublicKeyToString stub() {
		ConvertPublicKeyToString mock = mock(ConvertPublicKeyToString.class);
		when(mock.apply(PUBLIC_KEY)).thenReturn(PUBLIC_KEY_BASE64);
		return mock;
	}
}
