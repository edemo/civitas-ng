package civitas.crypto.decriptionshare.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.decriptionshare.VerifyElGamalDecryptionShare;
import civitas.crypto.publickey.ElGamalPublicKey;
import io.github.magwas.konveyor.testing.TestBase;

class VerifyElGamalDecryptionShareTest extends TestBase implements ElGamalDecryptionShareTestData {
	@InjectMocks
	VerifyElGamalDecryptionShare verifyElGamalDecryptionShare;

	@Test
	// @formatter:off
	@DisplayName(
			"""
			verify verifies the proof
			- ciphertext.a =? proof.g1
			- g =? proof.g2
			- proof.v =? share.ai
			- proof.w =? key
			- the proof verifies
			""")
	// @formatter:on
	void test4() {
		boolean actual =
				verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_E);
		verify(verifyElGamalDecryptionShare.verifyElGamalProofDiscLogEquality)
				.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE, EL_GAMAL_PARAMETERS);
		assertTrue(actual);
	}

	@Test
	@DisplayName("if the proof fails, returns false")
	void test() {
		boolean actual = verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE_BADPROOF, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_E);
		assertFalse(actual);
	}

	@Test
	@DisplayName("if g2 != g, it fails")
	void test4_1() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_E_BUT_OTHER_PARAMETERS));
	}

	@Test
	@DisplayName("if proof.v != share.ai, it fails")
	void testg() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE_BAD_AI, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("if proof.w != key, it fails")
	void testw() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify throws IllegalArgumentException if ciphertext is null")
	void test4_2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> assertFalse(verifyElGamalDecryptionShare.apply(
						EL_GAMAL_DECRYPTION_SHARE, null, EL_GAMAL_PUBLIC_KEY_EPRIME)));
	}

	@Test
	@DisplayName("verify throws IllegalArgumentException if key is null")
	void test4_3() {
		assertThrows(
				IllegalArgumentException.class,
				() -> assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE, CIPHERTEXT_E, null)));
	}

	@Test
	@DisplayName("verify is false if ciphertext.a != g1")
	void test4_4() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE, new ElGamalCiphertext(RANDOMS_2, BIGINT_A), EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify is false if the pubkey's g != g2")
	void test4_5() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKey(G_EXP_A, EL_GAMAL_PARAMETERS_GENERATOR_OTHER)));
	}

	@Test
	@DisplayName("verify is false if the pubkey's y != w ")
	void test4_6() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				EL_GAMAL_DECRYPTION_SHARE, EL_GAMAL_CIPHERTEXT, new ElGamalPublicKey(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("verify is false if  ai != v")
	void test4_7() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				new ElGamalDecryptionShare(BIGINT_A, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT),
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKey(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}
}
