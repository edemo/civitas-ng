package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.crypto.algorithms.CreatePermutation;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.algorithms.GetSharedKeyGenerator;
import civitas.util.Use;

public class CryptoFactoryCTest extends ConcreteTestBase
		implements PrivateKeyTestData, BasicValuesTestData {

	@Use
	CryptoHash cryptoHash;

	@Use
	GetPublicKeyGenerator getPublicKeyGenerator;

	@Test
	@Tag("functional")
	@DisplayName("publicKeyGenerator gives a generator which generates a public key with the given length")
	void publicKeyGeneratorTest() throws Exception {

		KeyPairGenerator generator = getPublicKeyGenerator.apply(KEYSIZE);

		RSAPublicKey publicKey = (RSAPublicKey) generator.generateKeyPair()
				.getPublic();
		assertEquals(1024, publicKey.getModulus().bitLength());

	}

	@Test
	@DisplayName("publicKeyGenerator returns the same object for two consecutive calls")
	void publicKeyGeneratorTest1() throws Exception {

		KeyPairGenerator generator = getPublicKeyGenerator.apply(KEYSIZE);
		KeyPairGenerator generator2 = getPublicKeyGenerator.apply(KEYSIZE);
		assertTrue(generator == generator2);

	}

	@Use
	GetSharedKeyGenerator getSharedKeyGenerator;

	@Test
	@DisplayName("sharedKeyGenerator returns a generator for the given key size")
	void sharedKeyGenerator() throws Exception {

		KeyGenerator generator = getSharedKeyGenerator.apply(KEYSIZE);

		SecretKeySpec key = (SecretKeySpec) generator.generateKey();
		assertEquals(KEYSIZE / 8, key.getEncoded().length);

	}

	@Test
	@DisplayName("sharedKeyGenerator returns the same object for two consecutive calls")
	void sharedKeyGenerator1() throws Exception {

		KeyGenerator generator = getSharedKeyGenerator.apply(KEYSIZE);
		KeyGenerator generator2 = getSharedKeyGenerator.apply(KEYSIZE);
		assertTrue(generator == generator2);

	}

	@Use
	CreatePermutation createPermutation;

	@Test
	@DisplayName("createPermutation creates a permutation of given size")
	void createPermutation() {

		int[] permutation = createPermutation.apply(SOME_SMALL_INT);
		boolean[] hasIt = new boolean[SOME_SMALL_INT];
		Arrays.fill(hasIt, false);
		for (int i = 0; i < SOME_SMALL_INT; i++) {
			hasIt[permutation[i]] = true;
		}
		for (int i = 0; i < SOME_SMALL_INT; i++) {
			if (!hasIt[i])
				fail();
		}
		assertTrue(true);
	}

}
