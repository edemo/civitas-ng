package civitas.crypto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;

import civitas.common.RandomAnswer;
import civitas.common.RandomFillerAnswer;
import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextTestData;
import civitas.crypto.rsaprivatekey.KeySpecMatcher;
import civitas.crypto.rsaprivatekey.KeySpecMatcherPrivate;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;
import civitas.crypto.sharedkey.SharedKeyTestData;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertextTestData;
import civitas.crypto.signature.SignatureTestData;

public class CryptoBaseStub
		implements PrivateKeyTestData,
				PublicKeyTestData,
				SharedKeyTestData,
				SignatureTestData,
				ElGamalParametersTestData,
				SharedKeyCiphertextTestData,
				PublicKeyCiphertextTestData {

	public static CryptoBase stub() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
		CryptoBase mock = mock(CryptoBase.class);
		mock.publicKeyFactory = mock(KeyFactory.class);
		mock.sharedKeyFactory = mock(SecretKeyFactory.class);
		mock.rsaSigner = mock(Signature.class);
		when(mock.publicKeyFactory.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC))))
				.thenReturn(PUBLIC_KEY);
		when(mock.publicKeyFactory.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC_BAD))))
				.thenThrow(new InvalidKeySpecException());
		when(mock.publicKeyFactory.generatePrivate(argThat(new KeySpecMatcherPrivate(KEYSPEC_PRIVATE2))))
				.thenThrow(new InvalidKeySpecException());
		when(mock.publicKeyFactory.generatePrivate(argThat(new KeySpecMatcherPrivate(KEYSPEC_PRIVATE))))
				.thenReturn(PRIVATE_KEY);
		when(mock.sharedKeyFactory.generateSecret(SHARED_KEY_SPEC)).thenReturn(SHARED_KEY_JS);
		when(mock.sharedKeyFactory.generateSecret(SHARED_KEY_SPEC_BAD)).thenThrow(new InvalidKeySpecException());
		when(mock.rsaSigner.sign()).thenReturn(SIGNATURE_OF_AUTH_NONCE_WITH_KEY_BYTES);
		when(mock.rsaSigner.verify(SIGNATURE_OF_AUTH_NONCE_WITH_KEY_BYTES)).thenReturn(true);
		when(mock.rsaSigner.verify(SIGNATURE_OF_SOMESTRING_WITH_KEY_BYTES)).thenReturn(true);
		doThrow(new InvalidKeyException()).when(mock.rsaSigner).initSign(PRIVATE_KEY_BAD);
		doThrow(new InvalidKeyException()).when(mock.rsaSigner).initVerify(PUBLIC_KEY_BAD);

		when(mock.obtainProbablePrime(eq(EL_GAMAL_KEY_LENGTH)))
				.thenReturn(BIGINT_B)
				.thenReturn(BIGINT_NO_PRIME_OF_KEYLENGTH)
				.thenReturn(BIGINT_Q)
				.thenThrow(new NullPointerException());
		when(mock.obtainProbablePrime(eq(SAFE_KEY_LENGTH))).thenReturn(BIGINT_B).thenReturn(SAFE_Q);

		when(mock.doCrypto(
						PUBLIC_KEY_CIPHER_ALG,
						PUBLIC_KEY_PROVIDER,
						PUBLIC_KEY,
						Cipher.ENCRYPT_MODE,
						SOMESTRING.getBytes()))
				.thenReturn(SOMESTRING_ENCRYPTED_BYTES);
		when(mock.doCrypto(
						PUBLIC_KEY_CIPHER_ALG,
						PUBLIC_KEY_PROVIDER,
						PRIVATE_KEY,
						Cipher.DECRYPT_MODE,
						SOMESTRING_ENCRYPTED_BYTES))
				.thenReturn(SOMESTRING.getBytes());
		when(mock.doCrypto(
						SHARED_KEY_CIPHER_ALG,
						SHARED_KEY_PROVIDER,
						SHARED_KEY_SPEC,
						Cipher.ENCRYPT_MODE,
						SOMESTRING.getBytes()))
				.thenReturn(SHARED_KEY_CIPHERTEXT_BYTES);
		when(mock.doCrypto(
						SHARED_KEY_CIPHER_ALG,
						SHARED_KEY_PROVIDER,
						SHARED_KEY_SPEC,
						Cipher.DECRYPT_MODE,
						SHARED_KEY_CIPHERTEXT_BYTES))
				.thenReturn(SOMESTRING.getBytes());

		when(mock.generateRandomElement(any())).thenAnswer(new RandomAnswer());
		when(mock.generateRandomElement(TWO_EXP_GROUP_LENGTH))
				.thenReturn(BIGINT_A)
				.thenReturn(BIGINT_NO_PRIME_OF_GROUPLENGTH)
				.thenReturn(BIGINT_A)
				.thenReturn(BIGINT_A)
				.thenReturn(BIGINT_NO_PRIME_OF_GROUPLENGTH)
				.thenReturn(BIGINT_P)
				.thenThrow(new NullPointerException());
		when(mock.generateRandomElement(BIGINT_P))
				.thenReturn(ONE)
				.thenReturn(BIGINT_P.subtract(ONE))
				.thenReturn(BIGINT_D);

		Random randomMock = mock(Random.class);
		doAnswer(new RandomFillerAnswer()).when(randomMock).nextBytes(any());
		when(randomMock.nextInt(SOME_SMALL_INT)).thenReturn(SOME_SMALL_INT - 1);
		when(mock.getRandomGenerator()).thenReturn(randomMock);

		KeyPair keypair = mock(KeyPair.class);
		when(keypair.getPrivate()).thenReturn(PRIVATE_KEY);
		when(keypair.getPublic()).thenReturn(PUBLIC_KEY);
		KeyPairGenerator generator = mock(KeyPairGenerator.class);
		when(generator.generateKeyPair()).thenReturn(keypair);
		when(mock.getPublicKeyGenerator(KEYSIZE)).thenReturn(generator);

		KeyGenerator sharedGenerator = mock(KeyGenerator.class);
		when(sharedGenerator.generateKey()).thenReturn(SHARED_KEY_SPEC);
		when(mock.getSharedKeyGenerator(SHARED_KEY_SIZE)).thenReturn(sharedGenerator);

		return mock;
	}
}
