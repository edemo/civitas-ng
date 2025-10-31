package civitas.crypto.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.ConvertPublicKeyToString;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.util.tests.BasicValuesTestData;

class SignWithPublicKeyTest extends RandomAwareTestBase
		implements PublicKeyTestData, SignatureTestData, BasicValuesTestData {

	@InjectMocks
	SignWithPublicKey signWithPublicKey;

	@Mock
	ConvertPublicKeyToString convertPublicKeyToString;

	@Mock
	CryptoBase cryptoBase;

	@Mock
	CryptoHash cryptoHash;

	@Test
	@DisplayName(
			"""
			signs with public key
			- initializes the rsa signer with the private key
			- updates the rsa signer with the byte array to sign
			- obtains the signature using the rsa signer
			- converts the public key to string
			- returns the signature containing the signature and the string version of the public key
			""")
	void test() throws CryptoException, InvalidKeyException, SignatureException {
		assertEquals(
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY,
				signWithPublicKey.apply(PRIVATE_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
		verify(convertPublicKeyToString).apply(PUBLIC_KEY);
		verify(cryptoBase.rsaSigner).initSign(PRIVATE_KEY);
		verify(cryptoBase.rsaSigner).update(AUTHENTICATION_NONCE.getBytes());
	}

	@Test
	@DisplayName("when the private key is bad, a CryptoException is thrown\n")
	void test2() {
		assertThrows(
				CryptoException.class,
				() -> signWithPublicKey.apply(PRIVATE_KEY_BAD, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
	}

	@Test
	@DisplayName(
			"""
			when signing a string, sings the hash of the string
			- computes the hash of the string
			- initializes the rsa signer with the private key
			- updates the rsa signer with the hash
			- obtains the signature using the rsa signer
			- converts the public key to string
			- returns the signature containing the signature and the string version of the public key
			""")
	void test1() throws CryptoException, InvalidKeyException, SignatureException {
		assertEquals(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, signWithPublicKey.apply(PRIVATE_KEY, PUBLIC_KEY, SOMESTRING));
		verify(cryptoHash).apply(SOMESTRING.getBytes());
		verify(convertPublicKeyToString).apply(PUBLIC_KEY);
		verify(cryptoBase.rsaSigner).initSign(PRIVATE_KEY);
		verify(cryptoBase.rsaSigner).update(SOMESTRING_HASH);
	}
}
