package civitas.crypto.signedciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextC;
import civitas.crypto.signedciphertext.SignAndEncrypt;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class SignAndEncryptTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {

	@Tested
	SignAndEncrypt signAndEncrypt;

	@Use
	CryptoHash cryptoHash;

	@Test
	@DisplayName("elGamalSignedEncrypt works as expected:" + "s:= random, "
			+ "a:=g^y (mod p), " + "b:=m*key^y (mod p), "
			+ "c:=hash(g^s,a,b,env) % q, " + "d:=s+c*y (mod q)")
	void elGamalSignedEncryptTest() throws Exception {

		CivitasBigInteger s = RANDOMS_0;
		CivitasBigInteger y = FACTOR_E;
		CivitasBigInteger msg = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;
		CivitasBigInteger key = PUBKEY_E;
		byte[] env = ADDITIONALENV_BYTES;

		CivitasBigInteger a = g.modPow(y, p);
		CivitasBigInteger b = msg.modMultiply(key.modPow(y, p), p);
		CivitasBigInteger c = cryptoHash.apply(g.modPow(s, p), a, b, env).mod(q);
		CivitasBigInteger d = s.modAdd(c.modMultiply(y, q), q);

		ElGamalSignedCiphertextC encrypt = (ElGamalSignedCiphertextC) signAndEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE,
						ELGAMAL_REENCRYPT_FACTOR_E, env);

		assertEquals(a, encrypt.a);
		assertEquals(b, encrypt.b);
		assertEquals(c, encrypt.c);
		assertEquals(d, encrypt.d);

	}

	@Test
	@Tag("testdata")
	@DisplayName("HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV")
	void constantCheck() {

		assertEquals(Util.fromBigInt(
				HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV),
				Util.fromBigInt(cryptoHash.apply(BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
						CIPHERTEXT_E_A, CIPHERTEXT_E_B, ADDITIONALENV_BYTES)
						.mod(BIGINT_Q)));

	}

}
