package civitas.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import civitas.crypto.rsapublickey.PublicKeyTestData;

public class GetServerPublicKeyStub implements PublicKeyTestData {
	public static GetPublicKey stub()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		GetPublicKey mock = mock(GetPublicKey.class);
		when(mock.apply(any(), any(), any())).thenReturn(PUBLIC_KEY);
		return mock;
	}
}
