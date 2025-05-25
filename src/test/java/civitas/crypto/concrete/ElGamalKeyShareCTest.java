package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamalKeyShare;

public class ElGamalKeyShareCTest extends ConcreteTestBase
		implements ElGamalKeyShareTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEY_SHARE_XML, EL_GAMAL_KEY_SHARE_C.toXML());
	}

	@Test
	@DisplayName("constructor with nulls work and can be an XML made of")
	void test_null() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEY_SHARE_NULL_XML,
				new ElGamalKeyShareC(null, null).toXML());
	}

	@Test
	@DisplayName("fromXML works")
	void test_fromXML() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEY_SHARE_XML, ElGamalKeyShareC
				.fromXML(new StringReader(EL_GAMAL_KEY_SHARE_XML)).toXML());
	}

	@Test
	@DisplayName("two instances are not equal if the instance of the proof is different with the same data"
			+ "FIXME: equals for ElGamalProofKnowDiscLogC is not overriden")
	void testEquals() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_C.equals(
				ElGamalKeyShareC.fromXML(new StringReader(EL_GAMAL_KEY_SHARE_XML))));
	}

	@Test
	@DisplayName("verify is true for a correctly constructed one")
	void test1() throws IllegalArgumentException, IOException {
		ElGamalKeyShareC share = EL_GAMAL_KEY_SHARE_C;
		assertTrue(share.verify());
	}

	@Test
	@DisplayName("verify is false if the proof is null")
	void test1_1() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY, null).verify());
	}

	@Test
	@DisplayName("verify is false if the key is null")
	void test1_2() throws IllegalArgumentException, IOException {
		assertFalse(
				new ElGamalKeyShareC(null, ELGAMAL_PROOF_KNOWN_DISC_LOG).verify());
	}

	@Test
	@DisplayName("verify is false if the key is not for the proof")
	void test1_3() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalKeyShareC(
				new ElGamalPublicKeyC(BIGINT_B, EL_GAMAL_PARAMETERS),
				ELGAMAL_PROOF_KNOWN_DISC_LOG).verify());
	}

	@Test
	@DisplayName("pubKey returns the public key")
	void pubKeyTest() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PUBLIC_KEY, EL_GAMAL_KEY_SHARE_C.pubKey());
	}

	@Test
	@DisplayName("proof returns the proof")
	void proofTest() throws IllegalArgumentException, IOException {
		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG, EL_GAMAL_KEY_SHARE_C.proof());
	}

	@Test
	@DisplayName("equals to other keyshare with same key and proof")
	void equalsTest() throws IllegalArgumentException, IOException {
		assertTrue(EL_GAMAL_KEY_SHARE_C.equals(new ElGamalKeyShareC(
				EL_GAMAL_PUBLIC_KEY, ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("not equals to other keyshare with different key")
	void equalsTest1() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_C.equals(new ElGamalKeyShareC(
				new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS),
				ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("not equals to other keyshare with different proof")
	void equalsTest2() throws IllegalArgumentException, IOException {
		assertFalse(
				EL_GAMAL_KEY_SHARE_C.equals(new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY,
						new ElGamalProofKnowDiscLogC(ELGAMAL_PROOF_KNOWN_DISC_LOG_A,
								BIGINT_C, BIGINT_A_ENCRYPTED_SAFE, BIGINT_A))));
	}

	@Test
	@DisplayName("not equals to other keyshare with different type")
	void equalsTest3() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_C.equals(mock(ElGamalKeyShare.class)));
	}

}
