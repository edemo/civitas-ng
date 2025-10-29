package civitas.crypto.rsapublickey.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.ConvertStringToPublicKey;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.tests.SignatureTestData;
import civitas.util.tests.BasicValuesTestData;
import io.github.magwas.konveyor.testing.TestUtil;

class VerifyPublicKeySignatureTest extends RandomAwareTestBase implements SignatureTestData, BasicValuesTestData {
	@InjectMocks
	VerifyPublicKeySignature verifyPublicKeySignature;

	@Test
	@DisplayName("verifies that the signature bytes are the signature of the bytes using the given public key")
	void test() throws CryptoException, InvalidKeyException, SignatureException, IllegalAccessException {
		assertTrue(verifyPublicKeySignature.apply(
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
		CryptoBase cryptoBase = TestUtil.dependency(verifyPublicKeySignature, CryptoBase.class);
		verify(cryptoBase.rsaSigner).initVerify(PUBLIC_KEY);
		verify(cryptoBase.rsaSigner).update(AUTHENTICATION_NONCE.getBytes());
		verify(cryptoBase.rsaSigner).verify(SIGNATURE_OF_AUTH_NONCE_WITH_KEY_BYTES);
	}

	@Test
	@DisplayName("if the verification fails, returns false")
	void test_1() throws CryptoException {
		assertFalse(
				verifyPublicKeySignature.apply(SIGNATURE_BAD_WITH_KEY, PUBLIC_KEY, AUTHENTICATION_NONCE.getBytes()));
	}

	@Test
	@DisplayName("if the key or the signature is bad, a  CryptoException is thrown")
	void test2() {
		assertThrows(
				CryptoException.class,
				() -> verifyPublicKeySignature.apply(
						SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY_BAD, AUTHENTICATION_NONCE.getBytes()));
	}

	@Test
	@DisplayName("in case no public key given, the one in the signature is used"
			+ "- converts the string in the sigature to a public key\n"
			+ "- uses the check expecting the public key")
	void test1() throws CryptoException, IllegalAccessException {
		assertTrue(verifyPublicKeySignature.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, AUTHENTICATION_NONCE.getBytes()));
		verify(TestUtil.dependency(verifyPublicKeySignature, ConvertStringToPublicKey.class))
				.apply(PUBLIC_KEY_BASE64);
	}

	@Test
	@DisplayName("bad signature does not check in case no public key given")
	void test1_1() throws CryptoException {
		assertFalse(verifyPublicKeySignature.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY2, AUTHENTICATION_NONCE.getBytes()));
	}

	@Test
	@DisplayName("when the message is a string, its hash is computed, and the signature of the hash is verified")
	void test3() throws CryptoException, InvalidKeyException, SignatureException, IllegalAccessException {
		boolean expected = verifyPublicKeySignature.apply(SIGNATURE_OF_SOMESTRING_WITH_KEY, PUBLIC_KEY, SOMESTRING);
		CryptoHash cryptoHash = TestUtil.dependency(verifyPublicKeySignature, CryptoHash.class);
		verify(cryptoHash).apply(SOMESTRING.getBytes());
		CryptoBase cryptoBase = TestUtil.dependency(verifyPublicKeySignature, CryptoBase.class);
		verify(cryptoBase.rsaSigner).initVerify(PUBLIC_KEY);
		verify(cryptoBase.rsaSigner).update(SOMESTRING_HASH);
		verify(cryptoBase.rsaSigner).verify(SIGNATURE_OF_SOMESTRING_WITH_KEY_BYTES);
		assertTrue(expected);
	}

	@Test
	@DisplayName("bad signature does not check when the message is a string")
	void test3_1() throws CryptoException {
		assertFalse(verifyPublicKeySignature.apply(SIGNATURE_BAD_WITH_KEY, PUBLIC_KEY, SOMESTRING));
	}

	@Test
	@DisplayName(
			"when the message is a string and no explicit public key is given, then the check uses the hash of the string and the public key from the signature")
	void test4() throws CryptoException, IllegalAccessException {
		boolean expected = verifyPublicKeySignature.apply(SIGNATURE_OF_SOMESTRING_WITH_KEY, SOMESTRING);
		verify(TestUtil.dependency(verifyPublicKeySignature, CryptoHash.class)).apply(SOMESTRING.getBytes());
		assertTrue(expected);
	}

	@Test
	@DisplayName("bad signature does not check when the message is a string and no explicit public key is given")
	void test4_1() throws CryptoException {
		assertFalse(verifyPublicKeySignature.apply(SIGNATURE_BAD_WITH_KEY, SOMESTRING_BASE64));
	}
}
