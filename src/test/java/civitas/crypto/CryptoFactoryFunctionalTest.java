package civitas.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.crypto.sharedkey.GetSharedKeyGenerator;
import civitas.util.Use;

@Tag("functional")
public class CryptoFactoryFunctionalTest extends TestBase
		implements PrivateKeyTestData, BasicValuesTestData {

	@Use
	CryptoHash cryptoHash;

	@Use
	GetPublicKeyGenerator getPublicKeyGenerator;
	@Use
	GetSharedKeyGenerator getSharedKeyGenerator;

	@Test
	@DisplayName("publicKeyGenerator gives a generator which generates a public key with the given length")
	void publicKeyGeneratorTest() throws Exception {

		KeyPairGenerator generator = getPublicKeyGenerator.apply(KEYSIZE);

		RSAPublicKey publicKey = (RSAPublicKey) generator.generateKeyPair()
				.getPublic();
		assertEquals(KEYSIZE, publicKey.getModulus().bitLength());

	}

	@Test
	@DisplayName("publicKeyGenerator returns the same object for two consecutive calls")
	void publicKeyGeneratorTest1() throws Exception {

		KeyPairGenerator generator = getPublicKeyGenerator.apply(KEYSIZE);
		KeyPairGenerator generator2 = getPublicKeyGenerator.apply(KEYSIZE);
		assertTrue(generator == generator2);

	}

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

}
