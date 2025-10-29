package civitas.crypto.algorithms.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.util.tests.BasicValuesTestData;

public class CreateFreshNonceBase64Stub implements BasicValuesTestData {
	public static CreateFreshNonceBase64 stub() {
		CreateFreshNonceBase64 mock = mock(CreateFreshNonceBase64.class);
		when(mock.apply(AUTHENTICATION_NONCE_LENGTH)).thenReturn(AUTHENTICATION_NONCE);
		return mock;
	}
}
