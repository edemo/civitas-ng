package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import civitas.crypto.rsakeypair.KeyPairTestData;

class GetPublicKeyGeneratorStub implements KeyPairTestData {
	public static GetPublicKeyGenerator stub() {
		KeyPair keypair = mock(KeyPair.class);
		when(keypair.getPrivate()).thenReturn(PRIVATE_KEY);
		when(keypair.getPublic()).thenReturn(PUBLIC_KEY);
		KeyPairGenerator generator = mock(KeyPairGenerator.class);
		when(generator.generateKeyPair()).thenReturn(keypair);
		GetPublicKeyGenerator mock = mock(GetPublicKeyGenerator.class);
		when(mock.apply(KEYSIZE)).thenReturn(generator);
		return mock;
	}
}
