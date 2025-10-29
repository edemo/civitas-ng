package civitas.common.tests;

import static org.mockito.Mockito.mock;

import civitas.common.GetPrivateKey;

public class GetPrivateKeyStub {
	public static GetPrivateKey stub() {
		return mock(GetPrivateKey.class);
	}
}
