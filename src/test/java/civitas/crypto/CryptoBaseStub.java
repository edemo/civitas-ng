package civitas.crypto;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;

import civitas.crypto.rsaprivatekey.KeySpecMatcher;
import civitas.crypto.rsaprivatekey.KeySpecMatcherPrivate;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public class CryptoBaseStub implements PrivateKeyTestData, PublicKeyTestData {

	public static CryptoBase stub() throws InvalidKeySpecException {
		CryptoBase mock = mock(CryptoBase.class);
		KeyFactory publicKeyFactory = mock(KeyFactory.class);
		when(publicKeyFactory.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC))))
				.thenReturn(PUBLIC_KEY_JS);
		when(publicKeyFactory
				.generatePrivate(argThat(new KeySpecMatcherPrivate(KEYSPEC_PRIVATE))))
				.thenReturn(PRIVATE_KEY_JS);
		mock.publicKeyFactory = publicKeyFactory;
		return mock;
	}

}
