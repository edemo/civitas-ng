package civitas.crypto.proofvote;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyProofVoteTest extends TestBase implements ProofVoteTestData {

	@Tested
	VerifyProofVote verifyProofVote;

	@Use
	VerifyProofVote verifyProofVoteReal;

	@Test
	@DisplayName("verify checks if c equals "
			+ "	 hash(params, encCapability, encChoice, context,"
			+ "	   g^s1*encCapability.a^c (mod p),"
			+ "	   g^s2*encChoice.a^c (mod p))")
	void test2_1() {

		assertTrue(verifyProofVote.apply(PROOF_VOTE, EL_GAMAL_PARAMETERS,
				CIPHERTEXT_ENCCAP, REENCRYPTED_WELL_KNOWN_CHOICE, ADDITIONALENV));
	}

	@Test
	@DisplayName("PROOF_VOTE_MAP")
	void test() {
		for (Integer piece : VOTE_PIECES) {
			assertTrue(verifyProofVoteReal.apply(PROOF_VOTE_MAP.get(piece),
					EL_GAMAL_PARAMETERS, ENCRYPTED_VOTE_CAPABILITIES.get(piece),
					REENCRYPTED_CHOICE_MAP.get(BALLOT.matrix[piece]),
					CONTEXT_MAP.get(piece)));
		}
	}
}
