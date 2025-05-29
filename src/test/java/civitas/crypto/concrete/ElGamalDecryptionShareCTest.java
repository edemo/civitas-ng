package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.algorithms.ElGamalDecryptionShareFromXML;
import civitas.crypto.algorithms.VerifyElGamalDecryptionShare;
import civitas.util.Tested;
import civitas.util.Use;

public class ElGamalDecryptionShareCTest extends ConcreteTestBase
		implements ElGamalDecryptionShareTestData, ElGamalPrivateKeyCTestData,
		ElGamalPublicKeyCTestData, ElGamalCiphertextCTestData {

	@Use
	ElGamalDecryptionShareFromXML elGamalDecryptionShareFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML,
				EL_GAMAL_DECRYPTION_SHARE.toXML());
	}

	@Test
	@DisplayName("constructor accepts nulls")
	void test1() {
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_NULL_XML,
				new ElGamalDecryptionShareC(null, null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML, elGamalDecryptionShareFromXML
				.apply(new StringReader(EL_GAMAL_DECRYPTION_SHARE_XML)).toXML());
	}

	@Test
	@DisplayName("getProof gets the proof")
	void test3() {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE,
				EL_GAMAL_DECRYPTION_SHARE.getProof());
	}

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
	@DisplayName("verify is false if the proof is null")
	void test4_1() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				new ElGamalDecryptionShareC(null, null), EL_GAMAL_CIPHERTEXT,
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify throws Error if ciphertext is null")
	void test4_2() {
		assertThrows(Error.class, () -> assertFalse(verifyElGamalDecryptionShare
				.apply(EL_GAMAL_DECRYPTION_SHARE, null, EL_GAMAL_PUBLIC_KEY_EPRIME)));
	}

	@Test
	@DisplayName("verify throws Error if ciphertext is not of type ElGamalCiphertextC")
	void test4_3() {
		assertThrows(Error.class,
				() -> assertFalse(
						verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
								mock(ElGamalCiphertext.class), EL_GAMAL_PUBLIC_KEY_EPRIME)));
	}

	@Test
	@DisplayName("verify is false if ciphertext.a != g1")
	void test4_4() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				new ElGamalCiphertextC(RANDOMS_2, BIGINT_A),
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify is false if the pubkey's g != g2")
	void test4_5() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKeyC(G_EXP_A, EL_GAMAL_PARAMETERS_GENERATOR_OTHER)));
	}

	@Test
	@DisplayName("verify is false if the pubkey's y != w ")
	void test4_6() {
		assertFalse(verifyElGamalDecryptionShare.apply(EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("verify is false if  ai != v")
	void test4_7() {
		assertFalse(verifyElGamalDecryptionShare.apply(
				new ElGamalDecryptionShareC(BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT),
				EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

}
