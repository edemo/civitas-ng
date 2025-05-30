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
import civitas.crypto.importing.ElGamalKeyShareFromXML;
import civitas.util.Use;

public class ElGamalKeyShareCTest extends ConcreteTestBase
		implements ElGamalKeyShareTestData {
	@Use
	ElGamalKeyShareFromXML elGamalKeyShareFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEY_SHARE_XML, EL_GAMAL_KEY_SHARE_E.toXML());
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
		assertEquals(EL_GAMAL_KEY_SHARE_XML, elGamalKeyShareFromXML
				.apply(new StringReader(EL_GAMAL_KEY_SHARE_XML)).toXML());
	}

	@Test
	@DisplayName("two instances are not equal if the instance of the proof is different with the same data"
			+ "FIXME: equals for ElGamalProofKnowDiscLogC is not overriden")
	void testEquals() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_E.equals(elGamalKeyShareFromXML
				.apply(new StringReader(EL_GAMAL_KEY_SHARE_XML))));
	}

	@Test
	@DisplayName("pubKey returns the public key")
	void pubKeyTest() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_KEY_SHARE_E.pubKey());
	}

	@Test
	@DisplayName("proof returns the proof")
	void proofTest() throws IllegalArgumentException, IOException {
		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG, EL_GAMAL_KEY_SHARE_E.proof());
	}

	@Test
	@DisplayName("equals to other keyshare with same key and proof")
	void equalsTest() throws IllegalArgumentException, IOException {
		assertTrue(EL_GAMAL_KEY_SHARE_E.equals(new ElGamalKeyShareC(
				EL_GAMAL_PUBLIC_KEY_E, ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("not equals to other keyshare with different key")
	void equalsTest1() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_E.equals(new ElGamalKeyShareC(
				new ElGamalPublicKeyC(BIGINT_A, EL_GAMAL_PARAMETERS),
				ELGAMAL_PROOF_KNOWN_DISC_LOG)));
	}

	@Test
	@DisplayName("not equals to other keyshare with different proof")
	void equalsTest2() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_E
				.equals(new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY_EPRIME,
						new ElGamalProofKnowDiscLogC(ELGAMAL_PROOF_KNOWN_DISC_LOG_A,
								BIGINT_C, SAFE_P_MINUS_A, BIGINT_A))));
	}

	@Test
	@DisplayName("not equals to other keyshare with different type")
	void equalsTest3() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_KEY_SHARE_E.equals(mock(ElGamalKeyShare.class)));
	}

}
