package civitas.crypto.proofdisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.Tested;
import civitas.util.Use;

public class ElGamalProofDiscLogEqualityCTest extends ConcreteTestBase
		implements ElGamalProofDiscLogEqualityCTestData {

	@Tested
	private static ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	@Use
	CryptoHash hash;
	@Use
	ElGamalProofDiscLogEqualityFromXML elGamalProofDiscLogEqualityFromXML;
	@Use
	ElGamalProofDiscLogEqualityToXML elGamalProofDiscLogEqualityToXML;
	@Use
	ConvertHashToBigInt convertHashToBigInt;

	@Test
	@DisplayName("constructor and toXML works as expected ")
	void test() throws IllegalArgumentException, IOException {

		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML,
				elGamalProofDiscLogEqualityToXML
						.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT));

	}

	@Test
	@DisplayName("construcProof constructs a proof "
			+ "To prove that log v = log w, where v = g_1^x and w = g_2^x,"
			+ "let: z = random in Z_q, a = g_1^z b = g_2^z c = hash(v,w,a,b) "
			+ "r = (z + cx) mod q		  The proof is (a,b,c,r). ")
	void construcProofTest() {

		ElGamalProofDiscLogEquality proof = constructElGamalDiscLogEqualityProof
				.apply(EL_GAMAL_PARAMETERS,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2, PRIVKEY_E);
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C_BASE64,
				Util.fromBigInt(proof.c));
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML,
				elGamalProofDiscLogEqualityToXML.apply(proof));

	}

	@Test
	@Tag("testdata")
	@DisplayName("testdata consistency check")
	void construcProofTest2() {

		ElGamalProofDiscLogEquality proof1 = constructElGamalDiscLogEqualityProof
				.apply(EL_GAMAL_PARAMETERS,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_G1,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_G2, PRIVKEY_E);
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_C_BASE64,
				Util.fromBigInt(proof1.c));
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_PROOF_XML,
				elGamalProofDiscLogEqualityToXML.apply(proof1));

	}

	@Tested
	VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	@Test
	@DisplayName("verify is true for a correctly costructed proof "
			+ "To verify, check that g_1^r = av^c (mod p) and g_2^r = bw^c (mod p).")
	void test0_1() {
		assertTrue(verifyElGamalProofDiscLogEquality.apply(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof ")
	void test0_2() {
		assertFalse(verifyElGamalProofDiscLogEquality.apply(
				new ElGamalProofDiscLogEquality(BIGINT_G, GENERATOR_OTHER, BIGINT_A,
						BIGINT_B, BIGINT_C, BIGINT_D, SAFE_P_MINUS_A, BIGINT_G_OTHER),
				EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof (other branch)")
	void test0_3() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamalProofDiscLogEquality.apply(
				((ElGamalProofDiscLogEquality) elGamalProofDiscLogEqualityFromXML
						.apply(
								new StringReader(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_BAD_W_XML))),
				EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false if the parameters are not of ElGamalParametersC")
	void test0_5() {
		assertFalse(verifyElGamalProofDiscLogEquality.apply(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				mock(ElGamalParameters.class)));
	}

	@Test
	@DisplayName("fromXML works as expected ")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				elGamalProofDiscLogEqualityFromXML.apply(new StringReader(
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML)));
	}

}
