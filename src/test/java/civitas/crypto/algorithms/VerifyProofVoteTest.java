package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ProofVoteTestData;
import civitas.util.Tested;

public class VerifyProofVoteTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData, ProofVoteTestData {

	@Tested
	VerifyProofVote verifyProofVote;

	@Test
	@DisplayName("verify checks if c equals "
			+ "	 hash(params, encCapability, encChoice, context,"
			+ "	   g^s1*encCapability.a^c (mod p),"
			+ "	   g^s2*encChoice.a^c (mod p))")
	void test2_1() {

		assertTrue(verifyProofVote.apply(PROOF_VOTE, EL_GAMAL_PARAMETERS,
				EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B, SOMESTRING_EXTENDED));
	}

}
