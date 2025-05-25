package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.algorithms.GetSharedKeyGenerator;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CryptoFactoryCTest extends ConcreteTestBase
		implements ConcreteTestData {

	@Use
	CryptoHash hash;

	@Test
	@DisplayName("elGamalEncrypt with a factor works as expected:"
			+ "c1:=g^y ; s:=h^y ; c2:=m * s  (mod p)")
	void test0() throws Exception {
		CivitasBigInteger y = CivitasBigInteger.valueOf(SOME_INT);
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger h = EL_GAMAL_PUBLIC_KEY.y;
		CivitasBigInteger c1 = BIGINT_G.modPow(y, BIGINT_P);
		CivitasBigInteger s = h.modPow(y, BIGINT_P);
		CivitasBigInteger c2 = msg.m.modMultiply(s, BIGINT_P);
		assertEquals(new ElGamalCiphertextC(c1, c2),
				(factory.elGamalEncrypt(EL_GAMAL_PUBLIC_KEY, msg,
						new ElGamalReencryptFactorC(y))));
	}

	@Test
	@DisplayName("elGamalEncrypt with factor zero results in c1=1, c2=msg"
			+ "FIXME: the pubkey is not used here. constructWellKnownCiphertexts uses this."
			+ "note from there:"
			+ "		// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...\n"
			+ "		// using the encryption factor 0. This is assumed by some of the\n"
			+ "		// zero knowledge proofs.")
	void test() throws Exception {
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		assertEquals(ENCRYPTED_ZERO_FACTOR_XML,
				((ElGamalCiphertextC) factory.elGamalEncrypt(EL_GAMAL_PUBLIC_KEY, msg,
						ENCRYPT_FACTOR_ZERO)).toXML());
	}

	@Test
	@DisplayName("elGamalVerify works as expected: "
			+ "c == hash(g^d * a^(-c), a, b, env) %q, where c and d are used mod q, others mod p")
	void elGamalVerifyTest() throws Exception {

		CivitasBigInteger s = CivitasBigInteger.valueOf(SOME_POSITIVE_INTEGER);
		CivitasBigInteger y = SOME_INT_BIG;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;
		CivitasBigInteger key = EL_GAMAL_PUBLIC_KEY.y;
		byte[] env = SOMESTRING_EXTENDED.getBytes();

		CivitasBigInteger a = g.modPow(y, p);
		CivitasBigInteger b = msg.m.modMultiply(key.modPow(y, p), p);
		CivitasBigInteger c = hash.apply(g.modPow(s, p), a, b, env).mod(q);
		CivitasBigInteger d = s.modAdd(c.modMultiply(y, q), q);

		ElGamalSignedCiphertextC cyphertext = new ElGamalSignedCiphertextC(a, b, c,
				d);

		assertTrue(factory.elGamalVerify(EL_GAMAL_PARAMETERS, cyphertext, env));

	}

	@Test
	@DisplayName("elGamalDecrypt decrypts and verifies the siged cyphertext")
	void elGamalDecryptTest() throws Exception {

		byte[] env = SOMESTRING_EXTENDED.getBytes();
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS);

		ElGamalSignedCiphertextC encrypted = (ElGamalSignedCiphertextC) factory
				.elGamalSignedEncrypt(EL_GAMAL_PUBLIC_KEY, msg,
						new ElGamalReencryptFactorC(SOME_INT_BIG), env);

		ElGamalMsgC decrypted = (ElGamalMsgC) factory
				.elGamalDecrypt(ELGAMAL_PRIVATE_KEY, encrypted, env);
		assertEquals(msg.m, decrypted.m);

	}

	@Test
	@DisplayName("elGamalDecrypt throws CryptoException when the verification fails")
	void elGamalDecryptTest1() throws Exception {

		byte[] env = SOMESTRING_EXTENDED.getBytes();
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS);

		ElGamalSignedCiphertextC encrypted = (ElGamalSignedCiphertextC) factory
				.elGamalSignedEncrypt(EL_GAMAL_PUBLIC_KEY, msg,
						new ElGamalReencryptFactorC(SOME_INT_BIG), env);

		assertThrows(CryptoException.class,
				() -> factory.elGamalDecrypt(ELGAMAL_PRIVATE_KEY, encrypted, null));

	}

	@Test
	@DisplayName("elGamalDecrypt decrypts non-signed cyphertext correctly")
	void elGamalDecryptTest2() throws Exception {

		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS);

		ElGamalCiphertext encrypted = factory.elGamalEncrypt(EL_GAMAL_PUBLIC_KEY,
				msg, new ElGamalReencryptFactorC(SOME_INT_BIG));

		assertEquals(msg.m, ((ElGamalMsgC) factory
				.elGamalDecrypt(ELGAMAL_PRIVATE_KEY, encrypted)).m);

	}

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

	@Test
	@DisplayName("createPermutation creates a permutation of given size")
	void createPermutation() {

		int[] permutation = factory.createPermutation(SOME_SMALL_INT);
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
