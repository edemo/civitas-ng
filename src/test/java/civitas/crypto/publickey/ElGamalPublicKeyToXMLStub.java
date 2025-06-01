package civitas.crypto.publickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElGamalPublicKeyToXMLStub implements ElGamalPublicKeyTestData {

	public static ElGamalPublicKeyToXML stub() {
		ElGamalPublicKeyToXML mock = mock(ElGamalPublicKeyToXML.class);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E))
				.thenReturn(EL_GAMAL_PUBLIC_KEY_E_XML);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME))
				.thenReturn(EL_GAMAL_PUBLIC_KEY_EPRIME_XML);
		return mock;
	}

}
