package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class ElGamalEncryptTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData, ElGamalReencryptFactorCTestData {

	@Tested
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("elGamalEncrypt with a random factor works as expected")
	void test0_1() throws Exception {

		CivitasBigInteger y = RANDOMS_0;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger h = EL_GAMAL_PUBLIC_KEY_EPRIME.y;
		CivitasBigInteger c1 = BIGINT_G.modPow(y, BIGINT_P);
		CivitasBigInteger s = h.modPow(y, BIGINT_P);
		CivitasBigInteger c2 = msg.m.modMultiply(s, BIGINT_P);
		ElGamalCiphertextC encrypt = (ElGamalCiphertextC) elGamalEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg);
		assertEquals(c1, encrypt.a);
		assertEquals(c2, encrypt.b);

	}

	@Test
	@DisplayName("elGamalEncrypt with a factor works as expected:"
			+ "c1:=g^factor ; s:=pubkey^factor ; c2:=m * s  (mod p)")
	void test0() throws Exception {
		CivitasBigInteger c1 = G_EXP_FACTOR;
		CivitasBigInteger s = PUBKEY_EPRIME.modPow(FACTOR_EPRIME, BIGINT_P);
		CivitasBigInteger c2 = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(s,
				BIGINT_P);
		ElGamalCiphertextC actual = (ElGamalCiphertextC) elGamalEncrypt.apply(
				EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE,
				new ElGamalReencryptFactorC(FACTOR_EPRIME));
		ElGamalCiphertextC ciphertext = new ElGamalCiphertextC(c1, c2);
		assertTrue(ciphertext.equals(actual));
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
		assertEquals(ENCRYPTED_ZERO_FACTOR, (elGamalEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg, ENCRYPT_FACTOR_ZERO)));
	}

}
