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

import civitas.crypto.ElGamalParameters;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.importing.ElGamalProofKnowDiscLogFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalProofKnowDiscLogCTest extends ConcreteTestBase
		implements ProofKnowDiscLogTestData {

	@Use
	CryptoHash hash;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {

		assertEquals(ELGAMAL_PROOF_KNOWN_DISC_LOG_XML,
				new ElGamalProofKnowDiscLogC(ELGAMAL_PROOF_KNOWN_DISC_LOG_A,
						ELGAMAL_PROOF_KNOWN_DISC_LOG_C, ELGAMAL_PROOF_KNOWN_DISC_LOG_R,
						PUBLICIZED_BIGINT_A).toXML());
	}

	@Test
	@DisplayName("verify checks that g^r = av^c (mod p)")
	void verifyTest() {

		CivitasBigInteger key = ELGAMAL_PRIVATE_KEY.x;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;

		CivitasBigInteger v = g.modPow(key, p);
		CivitasBigInteger z = RANDOMS_0;
		CivitasBigInteger a = g.modPow(z, p);
		CivitasBigInteger c = hash.apply(v, a).mod(q);
		CivitasBigInteger r = z.add(c.modMultiply(key, q));
		assertTrue(
				new ElGamalProofKnowDiscLogC(a, c, r, v).verify(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify fails if parameters are not of type ElGamalParametersC")
	void verifyTest1() {
		assertFalse(new ElGamalProofKnowDiscLogC(ELGAMAL_PROOF_KNOWN_DISC_LOG_A,
				ELGAMAL_PROOF_KNOWN_DISC_LOG_C, ELGAMAL_PROOF_KNOWN_DISC_LOG_R,
				PUBLICIZED_BIGINT_A).verify(mock(ElGamalParameters.class)));
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
		assertEquals(PUBLICIZED_BIGINT_A, actual.v);
	}

	@Test
	@DisplayName("fromXml throws NumberFormatException for empty values")
	void test3() throws IllegalArgumentException, IOException {
		assertThrows(NumberFormatException.class,
				() -> elGamalProofKnowDiscLogFromXML
						.apply(new StringReader(ELGAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML)));
	}

}
