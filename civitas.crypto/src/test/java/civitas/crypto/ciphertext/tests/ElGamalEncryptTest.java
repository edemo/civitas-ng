package civitas.crypto.ciphertext.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;

class ElGamalEncryptTest extends RandomAwareTestBase implements ElGamalCiphertextTestData {

	@InjectMocks
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("elGamalEncrypt with a random factor works as expected")
	void test0_1() {

		ElGamalMsg msg = new ElGamalMsg(BIGINT_G.modPow(BIGINT_B, BIGINT_P));
		ElGamalCiphertext encrypted = new ElGamalCiphertext(
				BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
				BIGINT_G.modPow(BIGINT_B, BIGINT_P)
						.modMultiply(EL_GAMAL_PUBLIC_KEY_EPRIME.y.modPow(RANDOMS_0, BIGINT_P), BIGINT_P));

		assertEquals(encrypted, elGamalEncrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg));
	}

	@Test
	@DisplayName(
			"elGamalEncrypt with a factor works as expected:" + "c1:=g^factor ; s:=pubkey^factor ; c2:=m * s  (mod p)")
	void test0() {
		assertEquals(
				ENCRYPTED_WITH_FACTOR_EPRIME,
				elGamalEncrypt.apply(
						EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE,
						new ElGamalReencryptFactor(FACTOR_EPRIME)));
	}

	@Test
	@DisplayName(
			"""
			elGamalEncrypt with factor zero results in c1=1, c2=msg
			FIXME: the pubkey is not used here. constructWellKnownCiphertexts uses this.
			note from there:
					// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...
					// using the encryption factor 0. This is assumed by some of the
					// zero knowledge proofs.
			""")
	void test() {
		ElGamalMsg msg = new ElGamalMsg(BIGINT_G.modPow(BIGINT_B, BIGINT_P));
		assertEquals(ENCRYPTED_ZERO_FACTOR, elGamalEncrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg, ENCRYPT_FACTOR_ZERO));
	}
}
