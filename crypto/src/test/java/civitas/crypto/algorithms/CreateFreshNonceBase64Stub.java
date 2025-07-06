package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.BasicValuesTestData;

public class CreateFreshNonceBase64Stub implements BasicValuesTestData {
	public static CreateFreshNonceBase64 stub() {
		CreateFreshNonceBase64 mock = mock(CreateFreshNonceBase64.class);
		when(mock.apply(AUTHENTICATION_NONCE_LENGTH))
				.thenReturn(AUTHENTICATION_NONCE);
		return mock;
	}

}
