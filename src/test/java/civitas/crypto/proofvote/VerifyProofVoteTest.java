package civitas.crypto.proofvote;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import civitas.util.Tested;

public class VerifyProofVoteTest extends TestBase
		implements VoteCapabilityShareTestData, ProofVoteTestData {

	@Tested
	VerifyProofVote verifyProofVote;

	@Test
	@DisplayName("verify checks if c equals "
			+ "	 hash(params, encCapability, encChoice, context,"
			+ "	   g^s1*encCapability.a^c (mod p),"
			+ "	   g^s2*encChoice.a^c (mod p))")
	void test2_1() {

		ProofVote proofVote = PROOF_VOTE;
		assertTrue(verifyProofVote.apply(proofVote, EL_GAMAL_PARAMETERS,
				CIPHERTEXT_ENCCAP, REENCRYPTED_WELL_KNOWN_CHOICE, ADDITIONALENV));
	}

}
