package civitas.crypto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.crypto.rsaprivatekey.KeySpecMatcher;
import civitas.crypto.rsaprivatekey.KeySpecMatcherPrivate;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;
import civitas.crypto.sharedkey.SharedKeyTestData;
import civitas.crypto.signature.SignatureTestData;

public class CryptoBaseStub implements PrivateKeyTestData, PublicKeyTestData,
		SharedKeyTestData, SignatureTestData, ElGamalParametersTestData {

	public static CryptoBase stub()
			throws InvalidKeySpecException, SignatureException, InvalidKeyException {
		CryptoBase mock = mock(CryptoBase.class);
		mock.publicKeyFactory = mock(KeyFactory.class);
		mock.sharedKeyFactory = mock(SecretKeyFactory.class);
		mock.rsaSigner = mock(Signature.class);
		when(mock.publicKeyFactory
				.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC))))
				.thenReturn(PUBLIC_KEY);
		when(mock.publicKeyFactory
				.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC_BAD))))
				.thenThrow(new InvalidKeySpecException());
		when(mock.publicKeyFactory
				.generatePrivate(argThat(new KeySpecMatcherPrivate(KEYSPEC_PRIVATE2))))
				.thenThrow(new InvalidKeySpecException());
		when(mock.publicKeyFactory
				.generatePrivate(argThat(new KeySpecMatcherPrivate(KEYSPEC_PRIVATE))))
				.thenReturn(PRIVATE_KEY);
		when(mock.sharedKeyFactory.generateSecret(SHARED_KEY_SPEC))
				.thenReturn(SHARED_KEY_JS);
		when(mock.sharedKeyFactory.generateSecret(SHARED_KEY_SPEC_BAD))
				.thenThrow(new InvalidKeySpecException());
		when(mock.rsaSigner.sign())
				.thenReturn(SIGNATURE_OF_AUTH_NONCE_WITH_KEY_BYTES);
		when(mock.rsaSigner.verify(SIGNATURE_OF_AUTH_NONCE_WITH_KEY_BYTES))
				.thenReturn(true);
		when(mock.rsaSigner.verify(SIGNATURE_OF_SOMESTRING_WITH_KEY_BYTES))
				.thenReturn(true);
		doThrow(new InvalidKeyException()).when(mock.rsaSigner)
				.initSign(PRIVATE_KEY_BAD);
		doThrow(new InvalidKeyException()).when(mock.rsaSigner)
				.initVerify(PUBLIC_KEY_BAD);

		when(
				mock.obtainProbablePrime(eq(EL_GAMAL_KEY_LENGTH), eq(CERTAINTY), any()))
				.thenReturn(BIGINT_B).thenReturn(BIGINT_NO_PRIME_OF_KEYLENGTH)
				.thenReturn(BIGINT_Q);
		when(mock.obtainProbablePrime(eq(SAFE_KEY_LENGTH), eq(CERTAINTY), any()))
				.thenReturn(BIGINT_B).thenReturn(SAFE_Q);

		return mock;
	}

}
