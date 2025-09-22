package civitas.crypto.proofdisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class ElGamalProofDiscLogEqualityTest extends RandomAwareTestBase
		implements ElGamalProofDiscLogEqualityTestData {

	@InjectMocks
	private static ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

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
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT, proof);

	}

}
