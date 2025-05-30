package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.importing.ElGamalProofKnowDiscLogFromXML;
import civitas.util.Use;

public class ElGamalProofKnowDiscLogCTest extends ConcreteTestBase
		implements ProofKnowDiscLogTestData {

	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {

		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_XML,
				new ElGamalProofKnowDiscLogC(ELGAMAL_PROOF_KNOWN_DISC_LOG_A,
						ELGAMAL_PROOF_KNOWN_DISC_LOG_C, ELGAMAL_PROOF_KNOWN_DISC_LOG_R,
						G_EXP_A).toXML());
	}

	@Test
	@DisplayName("constructor accepts nulls and toXML represents them as empty strings")
	void test1() {

		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML,
				new ElGamalProofKnowDiscLogC(null, null, null, null).toXML());
	}

	@Test
	@DisplayName("fromXml works as expected")
	void test2() throws IllegalArgumentException, IOException {
		ElGamalProofKnowDiscLogC actual = elGamalProofKnowDiscLogFromXML
				.apply(new StringReader(ELGAMAL_PROOF_KNOWN_DISC_LOG_XML));
		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_A, actual.a);
		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_C, actual.c);
		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_R, actual.r);
		assertEquals(G_EXP_A, actual.v);
	}

	@Test
	@DisplayName("fromXml throws NumberFormatException for empty values")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(NumberFormatException.class,
				() -> elGamalProofKnowDiscLogFromXML
						.apply(new StringReader(ELGAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML)));
	}

}
