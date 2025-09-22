package civitas.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import java.util.stream.IntStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.util.BasicValuesTestData;
import civitas.util.CivitasBigInteger;
import io.github.magwas.testing.TestBase;

class CryptoBaseTest extends TestBase implements Constants, BasicValuesTestData {

	@InjectMocks
	CryptoBase cryptoBase;

	@Test
	@DisplayName("we can generate a shared key, create an identical key "
			+ "using the shared key factory, and when we encrypt some bytes "
			+ "with the first key and decrypt it with the second one, we get our bytes back")
	void test() throws InvalidKeySpecException {

		SecretKey sharedKey =
				cryptoBase.getSharedKeyGenerator(SHARED_KEY_LENGTH).generateKey();
		SecretKeySpec skeySpec = new SecretKeySpec(sharedKey.getEncoded(), SHARED_KEY_ALG);

		SecretKey sharedKey2 = cryptoBase.sharedKeyFactory.generateSecret(skeySpec);

		byte[] encrypted =
				cryptoBase.doCrypto(SHARED_KEY_ALG, SHARED_KEY_PROVIDER, sharedKey, Cipher.ENCRYPT_MODE, BYTES);

		byte[] decrypted =
				cryptoBase.doCrypto(SHARED_KEY_ALG, SHARED_KEY_PROVIDER, sharedKey2, Cipher.DECRYPT_MODE, encrypted);

		assertArrayEquals(BYTES, decrypted);
	}

	@Test
	@DisplayName("getSharedKeyGenerator caches its result")
	void test4() {
		assertEquals(
				cryptoBase.getSharedKeyGenerator(SHARED_KEY_LENGTH),
				cryptoBase.getSharedKeyGenerator(SHARED_KEY_LENGTH));
	}

	@Test
	@DisplayName("getPublicKeyGenerator caches its result")
	void test5() {
		assertEquals(
				cryptoBase.getPublicKeyGenerator(PUBLIC_KEY_LENGTH),
				cryptoBase.getPublicKeyGenerator(PUBLIC_KEY_LENGTH));
	}

	@Test
	@DisplayName("getPublicKeyGenerator throws CryptoError for bad keylength")
	void test7() {
		assertThrows(CryptoError.class, () -> cryptoBase.getPublicKeyGenerator(-1));
	}

	@Test
	@DisplayName("doCrypto throws CryptoError if anything goes wrong")
	void test6() {
		SecretKeySpec skeySpec = new SecretKeySpec(SOMESTRING.getBytes(), SHARED_KEY_ALG);
		assertThrows(
				CryptoError.class,
				() -> cryptoBase.doCrypto(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER, skeySpec, Cipher.ENCRYPT_MODE, BYTES));
	}

	@Test
	@DisplayName("we can generate a public key pair, store them as byte array, "
			+ "recover them from the byte arrays, encrypt some bytes and "
			+ "decrypt the ciphertext correctly")
	void testPublic() throws InvalidKeySpecException {
		KeyPair keypair = cryptoBase.getPublicKeyGenerator(PUBLIC_KEY_LENGTH).generateKeyPair();
		PublicKey pubKey = keypair.getPublic();
		PrivateKey privKey = keypair.getPrivate();
		byte[] pubkeyBytes = pubKey.getEncoded();
		byte[] privkeyBytes = privKey.getEncoded();
		PublicKey pubKey2 = cryptoBase.publicKeyFactory.generatePublic(new X509EncodedKeySpec(pubkeyBytes));
		PrivateKey privKey2 = cryptoBase.publicKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(privkeyBytes));

		byte[] encrypted =
				cryptoBase.doCrypto(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER, pubKey2, Cipher.ENCRYPT_MODE, BYTES);

		byte[] decrypted =
				cryptoBase.doCrypto(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER, privKey2, Cipher.DECRYPT_MODE, encrypted);

		assertArrayEquals(BYTES, decrypted);
	}

	@Test
	@DisplayName("we can obtain a probable prime with a given bit length,"
			+ "and it probably is indeed a prime with the given bit length")
	void test1() {
		IntStream.range(0, RANDOM_RUNS).forEach(n -> {
			CivitasBigInteger prime = cryptoBase.obtainProbablePrime(BITLENGTH);
			assertEquals(BITLENGTH, prime.bitLength());
			assertTrue(prime.isProbablePrime(CERTAINTY));
		});
	}

	@Test
	@DisplayName("we can generate a random element for Q which is less than Q and " + "their bit lengths are similar")
	void testGenerate() {
		IntStream.range(0, RANDOM_RUNS).forEach(n -> {
			CivitasBigInteger element = cryptoBase.generateRandomElement(BIGINT_A);
			assertTrue(element.compareTo(BIGINT_A) <= 0);
			assertTrue(BIGINT_A.bitLength() >= element.bitLength());
		});
	}

	@Test
	@DisplayName("we can get a random generator which is up to date and secure")
	void test2() {
		Random generator = cryptoBase.getRandomGenerator();
		assertFalse(generator.isDeprecated());
		assertEquals(SecureRandom.class, generator.getClass());
	}

	@Test
	@DisplayName("we can get byte arrays of random numbers" + "(tested with the monoBit test from NIST 800-22 at 1%)")
	void test3() {
		byte[] bytes = new byte[SHARED_KEY_LENGTH];
		cryptoBase.nextBytes(bytes);
		monoBitTest(bytes);
	}

	public void monoBitTest(byte[] bytes) {
		int sum = 0;
		for (byte element : bytes) {
			int current = element;
			for (int j = 0; j < 8; j++) {
				int isOne = current & 1;
				current = current >>> 1;
				if (1 == isOne) {
					sum++;
				} else {
					sum--;
				}
			}
		}
		double s = Math.abs(sum) / Math.sqrt(bytes.length * 8) / Math.sqrt(2);
		assertTrue(s < 1.82, "S=" + s);
	}
}
