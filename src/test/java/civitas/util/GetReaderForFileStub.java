package civitas.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;

import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

class GetReaderForFileStub implements PublicKeyTestData, PrivateKeyTestData,
		ElGamalPrivateKeyTestData, ElGamalPublicKeyTestData {
	public static GetReaderForFile stub() throws FileNotFoundException {
		GetReaderForFile mock = mock(GetReaderForFile.class);
		when(mock.apply("public_key.xml"))
				.thenReturn(new BufferedReader(new StringReader(PUBLIC_KEY_XML)));
		when(mock.apply("el_gamal_private_key.xml")).thenReturn(
				new BufferedReader(new StringReader(EL_GAMAL_PRIVATE_KEY_E_XML)));
		when(mock.apply("el_gamal_public_key.xml")).thenReturn(
				new BufferedReader(new StringReader(EL_GAMAL_PUBLIC_KEY_E_XML)));
		when(mock.apply("rsa_private_key.xml"))
				.thenReturn(new BufferedReader(new StringReader(PRIVATE_KEY_XML)));
		return mock;

	}

}
