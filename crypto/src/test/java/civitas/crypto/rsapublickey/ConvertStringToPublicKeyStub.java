package civitas.crypto.rsapublickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bouncycastle.crypto.CryptoException;

public class ConvertStringToPublicKeyStub implements PublicKeyTestData {
	public static ConvertStringToPublicKey stub() throws CryptoException {
		ConvertStringToPublicKey mock = mock(ConvertStringToPublicKey.class);
		when(mock.apply(PUBLIC_KEY_BASE64)).thenReturn(PUBLIC_KEY);
		when(mock.apply(PUBLIC_KEY2_BASE64)).thenReturn(PUBLIC_KEY2);
		return mock;
	}
}
