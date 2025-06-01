package civitas.crypto.decriptionshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Tested;

public class VerifyElGamalDecryptionShareTest extends TestBase
		implements ElGamalDecryptionShareTestData {
	@Tested
	VerifyElGamalDecryptionShare verifyElGamalDecryptionShare;

	@Test
	@DisplayName("verify verifies the proof")
	void test4() {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_G1,
				CIPHERTEXT_E_A);
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_G2, BIGINT_G);
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_V,
				EL_GAMAL_DECRYPTION_SHARE_AI);
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_W, PUBKEY_E);
		boolean actual = verifyElGamalDecryptionShare
				.apply(EL_GAMAL_DECRYPTION_SHARE, CIPHERTEXT_E, EL_GAMAL_PUBLIC_KEY_E);
		verify(verifyElGamalDecryptionShare.verifyElGamalProofDiscLogEquality)
				.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE,
						EL_GAMAL_PARAMETERS);
		assertTrue(actual);
	}

	@Test
	@DisplayName("verify throws Error if ciphertext is null")
	void test4_2() {
		assertThrows(Error.class, () -> assertFalse(verifyElGamalDecryptionShare
				.apply(EL_GAMAL_DECRYPTION_SHARE, null, EL_GAMAL_PUBLIC_KEY_EPRIME)));
	}

	@Test
	@DisplayName("verify is false if ciphertext.a != g1")
	void test4_4() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				new ElGamalCiphertext(RANDOMS_2, BIGINT_A),
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify is false if the pubkey's g != g2")
	void test4_5() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKey(G_EXP_A, EL_GAMAL_PARAMETERS_GENERATOR_OTHER)));
	}

	@Test
	@DisplayName("verify is false if the pubkey's y != w ")
	void test4_6() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKey(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("verify is false if  ai != v")
	void test4_7() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				new ElGamalDecryptionShare(BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT),
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKey(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

}
