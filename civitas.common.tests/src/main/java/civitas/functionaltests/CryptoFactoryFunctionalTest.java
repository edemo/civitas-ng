package civitas.functionaltests;

import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.RandomAwareTestBase;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.util.BasicValuesTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class CryptoFactoryFunctionalTest extends RandomAwareTestBase implements PrivateKeyTestData, BasicValuesTestData {

	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	CryptoBase cryptoBase;

	@Test
	@DisplayName("publicKeyGenerator gives a generator which generates a public key with the given length")
	void publicKeyGeneratorTest() throws Exception {

		KeyPairGenerator generator = cryptoBase.getPublicKeyGenerator(KEYSIZE);

		RSAPublicKey publicKey = (RSAPublicKey) generator.generateKeyPair().getPublic();
		assertEquals(KEYSIZE, publicKey.getModulus().bitLength());
	}

	@Test
	@DisplayName("publicKeyGenerator returns the same object for two consecutive calls")
	void publicKeyGeneratorTest1() throws Exception {

		KeyPairGenerator generator = cryptoBase.getPublicKeyGenerator(KEYSIZE);
		KeyPairGenerator generator2 = cryptoBase.getPublicKeyGenerator(KEYSIZE);
		assertSame(generator, generator2);
	}

	@Test
	@DisplayName("sharedKeyGenerator returns a generator for the given key size")
	void sharedKeyGenerator() throws Exception {

		KeyGenerator generator = cryptoBase.getSharedKeyGenerator(KEYSIZE);

		SecretKeySpec key = (SecretKeySpec) generator.generateKey();
		assertEquals(KEYSIZE / 8, key.getEncoded().length);
	}

	@Test
	@DisplayName("sharedKeyGenerator returns the same object for two consecutive calls")
	void sharedKeyGenerator1() throws Exception {

		KeyGenerator generator = cryptoBase.getSharedKeyGenerator(KEYSIZE);
		KeyGenerator generator2 = cryptoBase.getSharedKeyGenerator(KEYSIZE);
		assertSame(generator, generator2);
	}
}
