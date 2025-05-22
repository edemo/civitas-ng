package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalKeyPair;
import civitas.crypto.ElGamalKeyPairShare;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;

public class CryptoFactoryCTest extends ConcreteTestBase
		implements ConcreteTestData {

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
	@DisplayName("elGamalEncrypt with a random factor works as expected")
	void test0_1() throws Exception {
		TestUtil.setUpFakeRandom();

		CivitasBigInteger y = RANDOMS_0;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger h = EL_GAMAL_PUBLIC_KEY.y;
		CivitasBigInteger c1 = BIGINT_G.modPow(y, BIGINT_P);
		CivitasBigInteger s = h.modPow(y, BIGINT_P);
		CivitasBigInteger c2 = msg.m.modMultiply(s, BIGINT_P);
		ElGamalCiphertextC encrypt = (ElGamalCiphertextC) factory
				.elGamalEncrypt(EL_GAMAL_PUBLIC_KEY, msg);
		assertEquals(c1, encrypt.a);
		assertEquals(c2, encrypt.b);
		TestUtil.tearDownFakeRandom();

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
	@DisplayName("elGamalReencrypt works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1() throws Exception {
		TestUtil.setUpFakeRandom();

		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger y = RANDOMS_0;
		CivitasBigInteger m = EL_GAMAL_PUBLIC_KEY.y;
		CivitasBigInteger c1 = BIGINT_A;
		CivitasBigInteger c2 = BIGINT_B;

		ElGamalCiphertext cipherText = new ElGamalCiphertextC(c1, c2);

		c1 = c1.modMultiply(g.modPow(y, p), p);
		c2 = c2.modMultiply(m.modPow(y, p), p);

		assertEquals(new ElGamalCiphertextC(c1, c2),
				factory.elGamalReencrypt(EL_GAMAL_PUBLIC_KEY, cipherText));

		TestUtil.tearDownFakeRandom();
	}

	@Test
	@DisplayName("elGamalReencrypt with a factor works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1_1() throws Exception {

		CivitasBigInteger y = CivitasBigInteger.valueOf(SOME_POSITIVE_INTEGER);
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger m = EL_GAMAL_PUBLIC_KEY.y;

		CivitasBigInteger c1 = BIGINT_A;
		CivitasBigInteger c2 = BIGINT_B;
		ElGamalCiphertext cipherText = new ElGamalCiphertextC(c1, c2);

		c1 = c1.modMultiply(g.modPow(y, p), p);
		c2 = c2.modMultiply(m.modPow(y, p), p);

		assertEquals(new ElGamalCiphertextC(c1, c2), factory.elGamalReencrypt(
				EL_GAMAL_PUBLIC_KEY, cipherText, new ElGamalReencryptFactorC(y)));

	}

	@Test
	@DisplayName("generateElGamalKeyPair works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateElGamalKeyPairtest() {
		TestUtil.setUpFakeRandom();
		ElGamalKeyPair keyPair = factory
				.generateElGamalKeyPair(EL_GAMAL_PARAMETERS);

		assertEquals(RANDOMS_0, ((ElGamalPrivateKeyC) keyPair.privateKey()).x);
		assertEquals(RANDOMS_0_PUBLISHED,
				((ElGamalPublicKeyC) keyPair.publicKey()).y);

		TestUtil.tearDownFakeRandom();

	}

	@Test
	@DisplayName("generateKeyPairShare works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateKeyPairShareTest() {
		TestUtil.setUpFakeRandom();

		ElGamalKeyPairShare keyPair = factory
				.generateKeyPairShare(EL_GAMAL_PARAMETERS);

		assertEquals(RANDOMS_0, ((ElGamalPrivateKeyC) keyPair.privKey).x);
		assertEquals(RANDOMS_0_PUBLISHED, ((ElGamalPublicKeyC) keyPair.pubKey).y);

		TestUtil.tearDownFakeRandom();

	}

	@Test
	@DisplayName("elGamalSignedEncrypt works as expected:" + "s:= random, "
			+ "a:=g^y (mod p), " + "b:=m*key^y (mod p), "
			+ "c:=hash(g^s,a,b,env) % q, " + "d:=s+c*y (mod q)")
	void elGamalSignedEncryptTest() throws Exception {

		TestUtil.setUpFakeRandom();

		CivitasBigInteger s = RANDOMS_0;
		CivitasBigInteger y = SOME_INT_BIG;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;
		CivitasBigInteger key = EL_GAMAL_PUBLIC_KEY.y;
		byte[] env = SOMESTRING_EXTENDED.getBytes();

		CivitasBigInteger a = g.modPow(y, p);
		CivitasBigInteger b = msg.m.modMultiply(key.modPow(y, p), p);
		CivitasBigInteger c = factory.hash(g.modPow(s, p), a, b, env).mod(q);
		CivitasBigInteger d = s.modAdd(c.modMultiply(y, q), q);

		ElGamalSignedCiphertextC encrypt = (ElGamalSignedCiphertextC) factory
				.elGamalSignedEncrypt(EL_GAMAL_PUBLIC_KEY, msg,
						new ElGamalReencryptFactorC(y), env);

		assertEquals(a, encrypt.a);
		assertEquals(b, encrypt.b);
		assertEquals(c, encrypt.c);
		assertEquals(d, encrypt.d);

		TestUtil.tearDownFakeRandom();
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
		CivitasBigInteger c = factory.hash(g.modPow(s, p), a, b, env).mod(q);
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

	@Test
	@DisplayName("constructProofKnowDiscLog constructs the proof correctly:"
			+ "v:=g^key (mod p), " + "z:=random, " + "a:=g^z (mod p), "
			+ "c:=hash(v,a)%q, " + "r:=z+c*key (mod q)")
	void constructProofKnowDiscLogTest() throws Exception {

		TestUtil.setUpFakeRandom();

		CivitasBigInteger key = ELGAMAL_PRIVATE_KEY.x;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;

		CivitasBigInteger v = g.modPow(key, p);
		CivitasBigInteger z = RANDOMS_0;
		CivitasBigInteger a = g.modPow(z, p);
		CivitasBigInteger c = factory.hash(v, a).mod(q);
		CivitasBigInteger r = z.modAdd(c.modMultiply(key, q), q);

		ElGamalProofKnowDiscLogC expected = new ElGamalProofKnowDiscLogC(a, c, r,
				v);

		ElGamalProofKnowDiscLogC proof = (ElGamalProofKnowDiscLogC) factory
				.constructProofKnowDiscLog(EL_GAMAL_PARAMETERS, ELGAMAL_PRIVATE_KEY);

		assertEquals(
				"<elGamalProofKnowDiscLog><a>cZP5shp3SOALmUwEY6Zj6crdGhQzIB7fkbIS9sLCUzI4tZDlzROdfCm6EHz1sd5ekqrVwzRfwKBBuOrGeoujtfXJ7e1U5nxgCzcXTJAwhIsuM0KVyvgPV8krRzzRf1/hP7hSwzh4eWCpWKmzfREuoJBe0RKnrv+jBi0U46tSRRgz9qg5x3K92fTEtiCWQnLb5JlA+VWbJNxsdGm99o2t4wcmRdK2ZmOYFPdxmpCLLV5eCWCHCO/rZxBtWwhKScvwhjjbcnSlefNb4MXjEwk3j97Uulz7rt35KKDUMmcxDjpwHbkoAl7lbhtXNPgaPIrcIM60DDdclM8cwXz1ItvUbOzlprPLgjyskGj4g0Kmz+K6v/NfFRCKM2FA9QKlMSlpGWGsQt68+EYAvgTNAdoiSNdoDP+YBY1B6ITg384VcHOIzBhORrLupPR9iPUoIvU0GelBNciOhXjnFUjuWTSpsoCj47N9GBq9GGsYXpTBuXTMI/9wIsqUPT+sjOrvQERl</a><c>V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=</c><r>ElIxK5N4qOvnJH6VBw+5us9mO1M/Y0t8WhDxjNKnHXk=</r><v>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</v></elGamalProofKnowDiscLog>",
				ELGAMAL_PROOF_KNOWN_DISC_LOG_XML);
		assertEquals(expected.toXML(), proof.toXML());
		TestUtil.setUpFakeRandom();

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the key is null")
	void constructProofKnowDiscLogTest1() throws Exception {

		assertNull(factory.constructProofKnowDiscLog(EL_GAMAL_PARAMETERS, null));

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the key is not ElGamalPrivateKeyC")
	void constructProofKnowDiscLogTest2() throws Exception {

		assertNull(factory.constructProofKnowDiscLog(EL_GAMAL_PARAMETERS,
				mock(ElGamalPrivateKey.class)));

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the parameters is null")
	void constructProofKnowDiscLogTest3() throws Exception {

		assertNull(factory.constructProofKnowDiscLog(null, ELGAMAL_PRIVATE_KEY));

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if parameters are not ElGamalParametersC")
	void constructProofKnowDiscLogTest4() throws Exception {

		assertNull(factory.constructProofKnowDiscLog(mock(ElGamalParameters.class),
				ELGAMAL_PRIVATE_KEY));

	}

	@Test
	@DisplayName("constructDecryptionShare works as expected: "
			+ "ai:=c1^key (mod p), " + "g1:= c1, " + "g2:= g, "
			+ "v:= g1^key (mod p), " + "w:= g2^key (mod p), " + "z:= random,"
			+ "a:= g1^z (mod p), " + "b:= g2^z (mod p), " + "c:= hash(v,w,a,b) % q, "
			+ "r:= z+c*key (mod q)   " + "FIXME: is using g as g2 ok?")
	void constructDecryptionShareTest() throws Exception {

		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_A, EL_GAMAL_PARAMETERS);

		ElGamalCiphertextC encrypted = (ElGamalCiphertextC) factory.elGamalEncrypt(
				EL_GAMAL_PUBLIC_KEY, msg, new ElGamalReencryptFactorC(SOME_INT_BIG));

		TestUtil.setUpFakeRandom();

		CivitasBigInteger key = ELGAMAL_PRIVATE_KEY.x;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;
		CivitasBigInteger c1 = encrypted.a;

		CivitasBigInteger ai = c1.modPow(key, p);
		CivitasBigInteger g1 = c1;
		CivitasBigInteger g2 = g;
		CivitasBigInteger v = g1.modPow(key, p);
		CivitasBigInteger w = g2.modPow(key, p);
		CivitasBigInteger z = RANDOMS_0;
		CivitasBigInteger a = g1.modPow(z, p);
		CivitasBigInteger b = g2.modPow(z, p);
		CivitasBigInteger c = factory
				.hashToBigInt(
						factory.hash(Arrays.asList(new CivitasBigInteger[] { v, w, a, b })))
				.mod(q);
		CivitasBigInteger r = z.modAdd(c.modMultiply(key, q), q);

		ElGamalKeyPairShare keyShare = new ElGamalKeyPairShare(EL_GAMAL_PARAMETERS,
				EL_GAMAL_PUBLIC_KEY, ELGAMAL_PRIVATE_KEY);

		ElGamalDecryptionShareC actual = (ElGamalDecryptionShareC) factory
				.constructDecryptionShare(encrypted, keyShare);

		assertEquals(ai, actual.ai);
		assertEquals(g1, actual.proof.g1);
		assertEquals(g2, actual.proof.g2);
		assertEquals(a, actual.proof.a);
		assertEquals(b, actual.proof.b);
		assertEquals(c, actual.proof.c);
		assertEquals(v, actual.proof.v);
		assertEquals(w, actual.proof.w);
		assertEquals(r, actual.proof.r);

		TestUtil.tearDownFakeRandom();

	}

	@Test
	@DisplayName("publicKeyGenerator gives a generator which generates a public key with the given length")
	void publicKeyGeneratorTest() throws Exception {

		KeyPairGenerator generator = factory.publicKeyGenerator(KEYSIZE);

		RSAPublicKey publicKey = (RSAPublicKey) generator.generateKeyPair()
				.getPublic();
		assertEquals(1024, publicKey.getModulus().bitLength());

	}

	@Test
	@DisplayName("publicKeyGenerator returns the same object for two consecutive calls")
	void publicKeyGeneratorTest1() throws Exception {

		KeyPairGenerator generator = factory.publicKeyGenerator(KEYSIZE);
		KeyPairGenerator generator2 = factory.publicKeyGenerator(KEYSIZE);
		assertTrue(generator == generator2);

	}

	@Test
	@DisplayName("sharedKeyGenerator returns a generator for the given key size")
	void sharedKeyGenerator() throws Exception {

		KeyGenerator generator = factory.sharedKeyGenerator(KEYSIZE);

		SecretKeySpec key = (SecretKeySpec) generator.generateKey();
		assertEquals(KEYSIZE / 8, key.getEncoded().length);

	}

	@Test
	@DisplayName("sharedKeyGenerator returns the same object for two consecutive calls")
	void sharedKeyGenerator1() throws Exception {

		KeyGenerator generator = factory.sharedKeyGenerator(KEYSIZE);
		KeyGenerator generator2 = factory.sharedKeyGenerator(KEYSIZE);
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
