package civitas.crypto.proofknowndisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalProofKnowDiscLogCTest extends ConcreteTestBase
		implements ProofKnowDiscLogTestData {

	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	@Test
	@Tag("testdata")
	@DisplayName("EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64")
	void testHash() {
		CivitasBigInteger hash = cryptoHash
				.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG_V, EL_GAMAL_PROOF_KNOWN_DISC_LOG_A)
				.mod(BIGINT_Q);
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64, Util.fromBigInt(hash));

	}

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {

		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_XML,
				new ElGamalProofKnowDiscLogC(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A,
						EL_GAMAL_PROOF_KNOWN_DISC_LOG_C, EL_GAMAL_PROOF_KNOWN_DISC_LOG_R,
						G_EXP_A).toXML());
	}

	@Test
	@DisplayName("constructor accepts nulls and toXML represents them as empty strings")
	void test1() {

		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML,
				new ElGamalProofKnowDiscLogC(null, null, null, null).toXML());
	}

	@Test
	@DisplayName("fromXml works as expected")
	void test2() throws IllegalArgumentException, IOException {
		ElGamalProofKnowDiscLogC actual = elGamalProofKnowDiscLogFromXML
				.apply(new StringReader(EL_GAMAL_PROOF_KNOWN_DISC_LOG_XML));
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A, actual.a);
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_C, actual.c);
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, actual.r);
		assertEquals(G_EXP_A, actual.v);
	}

	@Test
	@DisplayName("fromXml throws NumberFormatException for empty values")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(NumberFormatException.class,
				() -> elGamalProofKnowDiscLogFromXML
						.apply(new StringReader(EL_GAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML)));
	}

}
