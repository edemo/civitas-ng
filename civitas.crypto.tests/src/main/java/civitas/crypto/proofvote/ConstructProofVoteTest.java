package civitas.crypto.proofvote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.CryptoException;

class ConstructProofVoteTest extends RandomAwareTestBase implements ProofVoteTestData {

	@InjectMocks
	ConstructProofVote constructProofVote;

	@Test
	@DisplayName("constructs a proof that the vote data is correct"
			+ "		 r1 = random in Z_q,                          "
			+ "		 r2 = random in Z_q,                           "
			+ "		 a = g^r1,                                      "
			+ "		 b = g^r2,                           "
			+ "		 c = hash(g, encCapability (a and b), encChoice (a and b), context,a,b,g^r1,g^r2) mod q, "
			+ "		 s1 = r1-c*alpha1 (mod q),                           "
			+ "		 s2 = r2-c*alpha2 (mod q) ")
	void test2() throws CryptoException {

		ProofVote proofVote = constructProofVote.apply(
				EL_GAMAL_PARAMETERS,
				CIPHERTEXT_ENCCAP,
				REENCRYPTED_WELL_KNOWN_CHOICE,
				ADDITIONALENV,
				ELGAMAL_REENCRYPT_FACTOR_EPRIME,
				ELGAMAL_REENCRYPT_FACTOR_E);
		assertEquals(PROOF_VOTE, proofVote);
	}
}
