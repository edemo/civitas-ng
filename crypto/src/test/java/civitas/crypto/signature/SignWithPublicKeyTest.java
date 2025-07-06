package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

class SignWithPublicKeyTest extends TestBase
		implements PublicKeyTestData, SignatureTestData, BasicValuesTestData {

	@InjectMocks
	SignWithPublicKey signWithPublicKey;

	@Test
	@DisplayName("signs with public key\n"
			+ "- initializes the rsa signer with the private key\n"
			+ "- updates the rsa signer with the byte array to sign\n"
			+ "- obtains the signature using the rsa signer\n"
			+ "- converts the public key to string\n"
			+ "- returns the signature containing the signature and the string version of the public key\n")
	void test() throws CryptoException, InvalidKeyException, SignatureException {
		assertEquals(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, signWithPublicKey
				.apply(PRIVATE_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
		verify(signWithPublicKey.convertPublicKeyToString).apply(PUBLIC_KEY);
		verify(signWithPublicKey.cryptoBase.rsaSigner).initSign(PRIVATE_KEY);
		verify(signWithPublicKey.cryptoBase.rsaSigner)
				.update(AUTHENTICATION_NONCE.getBytes());
	}

	@Test
	@DisplayName("when the private key is bad, a CryptoException is thrown\n")
	void test2() {
		assertThrows(CryptoException.class, () -> signWithPublicKey
				.apply(PRIVATE_KEY_BAD, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
	}

	@Test
	@DisplayName("when signing a string, sings the hash of the string"
			+ "- computes the hash of the string"
			+ "- initializes the rsa signer with the private key\n"
			+ "- updates the rsa signer with the hash\n"
			+ "- obtains the signature using the rsa signer\n"
			+ "- converts the public key to string\n"
			+ "- returns the signature containing the signature and the string version of the public key\n")
	void test1() throws CryptoException, InvalidKeyException, SignatureException {
		assertEquals(SIGNATURE_OF_AUTH_NONCE_WITH_KEY,
				signWithPublicKey.apply(PRIVATE_KEY, PUBLIC_KEY, SOMESTRING));
		verify(signWithPublicKey.cryptoHash).apply(SOMESTRING.getBytes());
		verify(signWithPublicKey.convertPublicKeyToString).apply(PUBLIC_KEY);
		verify(signWithPublicKey.cryptoBase.rsaSigner).initSign(PRIVATE_KEY);
		verify(signWithPublicKey.cryptoBase.rsaSigner).update(SOMESTRING_HASH);
	}

}
