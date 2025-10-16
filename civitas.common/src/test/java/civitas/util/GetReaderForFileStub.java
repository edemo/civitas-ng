package civitas.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public class GetReaderForFileStub
		implements PublicKeyTestData, PrivateKeyTestData, ElGamalPrivateKeyTestData, ElGamalPublicKeyTestData {
	public static GetReaderForFile stub() throws IOException {
		GetReaderForFile mock = mock(GetReaderForFile.class);
		when(mock.apply(EL_GAMAL_PRIVATE_KEY_FILE))
				.thenReturn(new BufferedReader(new StringReader(EL_GAMAL_PRIVATE_KEY_E_XML)));
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_FILE))
				.thenReturn(new BufferedReader(new StringReader(EL_GAMAL_PUBLIC_KEY_E_XML)));
		when(mock.apply(PRIVATE_KEY_FILE)).thenReturn(new BufferedReader(new StringReader(PRIVATE_KEY_XML)));
		return mock;
	}
}
