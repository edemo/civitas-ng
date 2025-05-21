package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamalCiphertext;

public class ElGamalDecryptionShareCTest extends ConcreteTestBase {

	private static final ElGamalDecryptionShareC EL_GAMAL_DECRYPTION_SHARE = new ElGamalDecryptionShareC(
			PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A,
			EL_GAMAL_PROOF_DISC_LOG_EQUALITY);

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
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML, ElGamalDecryptionShareC
				.fromXML(new StringReader(EL_GAMAL_DECRYPTION_SHARE_XML)).toXML());
	}

	@Test
	@DisplayName("getProof gets the proof")
	void test3() {
		assertEquals(EL_GAMAL_PROOF_DISC_LOG_EQUALITY,
				EL_GAMAL_DECRYPTION_SHARE.getProof());
	}

	@Test
	@DisplayName("verify verifies the proof")
	void test4() {
		assertTrue(EL_GAMAL_DECRYPTION_SHARE.verify(EL_GAMAL_CIPHERTEXT,
				EL_GAMAL_PUBLIC_KEY));
	}

	@Test
	@DisplayName("verify is false if the proof is null")
	void test4_1() {
		assertFalse(new ElGamalDecryptionShareC(null, null)
				.verify(EL_GAMAL_CIPHERTEXT, EL_GAMAL_PUBLIC_KEY));
	}

	@Test
	@DisplayName("verify throws Error if ciphertext is null")
	void test4_2() {
		assertThrows(Error.class, () -> assertFalse(
				EL_GAMAL_DECRYPTION_SHARE.verify(null, EL_GAMAL_PUBLIC_KEY)));
	}

	@Test
	@DisplayName("verify throws Error if ciphertext is not of type ElGamalCiphertextC")
	void test4_3() {
		assertThrows(Error.class, () -> assertFalse(EL_GAMAL_DECRYPTION_SHARE
				.verify(mock(ElGamalCiphertext.class), EL_GAMAL_PUBLIC_KEY)));
	}

	@Test
	@DisplayName("verify is false if ciphertext.a != g1")
	void test4_4() {
		assertFalse(EL_GAMAL_DECRYPTION_SHARE.verify(
				new ElGamalCiphertextC(RANDOMS_2, BIGINT_A), EL_GAMAL_PUBLIC_KEY));
	}

	@Test
	@DisplayName("verify is false if the pubkey's g != g2")
	void test4_5() {
		assertFalse(EL_GAMAL_DECRYPTION_SHARE.verify(EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKeyC(PUBLICIZED_BIGINT_A,
						EL_GAMAL_PARAMETERS_GENERATOR_OTHER)));
	}

	@Test
	@DisplayName("verify is false if the pubkey's y != w ")
	void test4_6() {
		assertFalse(EL_GAMAL_DECRYPTION_SHARE.verify(EL_GAMAL_CIPHERTEXT,
				new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("verify is false if  ai != v")
	void test4_7() {
		assertFalse(
				new ElGamalDecryptionShareC(BIGINT_A, EL_GAMAL_PROOF_DISC_LOG_EQUALITY)
						.verify(EL_GAMAL_CIPHERTEXT,
								new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS)));
	}

}
