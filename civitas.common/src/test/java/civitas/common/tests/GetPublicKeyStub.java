package civitas.common.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import civitas.common.GetPublicKey;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;

public class GetPublicKeyStub implements PublicKeyTestData {
	public static GetPublicKey stub()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		GetPublicKey mock = mock(GetPublicKey.class);
		when(mock.apply(any(), any(), eq("bbs"))).thenReturn(PUBLIC_KEY);
		return mock;
	}
}
