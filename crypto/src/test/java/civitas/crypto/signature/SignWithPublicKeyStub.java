package civitas.crypto.signature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.CryptoError;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public class SignWithPublicKeyStub
		implements PublicKeyTestData, BasicValuesTestData, SignatureTestData {
	public static SignWithPublicKey stub() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, SignatureException,
			CryptoError, CryptoException {
		SignWithPublicKey mock = mock(SignWithPublicKey.class);

		when(mock.apply(PRIVATE_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE))
				.thenReturn(SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		when(mock.apply(PRIVATE_KEY2, PUBLIC_KEY2, AUTHENTICATION_NONCE))
				.thenReturn(SIGNATURE_OF_AUTH_NONCE_WITH_KEY2);
		return mock;
	}

}
