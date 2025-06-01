package civitas.crypto.proofdisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.Util;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.util.Tested;
import civitas.util.Use;

public class ElGamalProofDiscLogEqualityCTest extends TestBase
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

	@Test
	@DisplayName("fromXML works as expected ")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				elGamalProofDiscLogEqualityFromXML.apply(new StringReader(
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML)));
	}

}
