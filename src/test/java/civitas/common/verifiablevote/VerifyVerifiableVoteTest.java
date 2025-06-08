package civitas.common.verifiablevote;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;
import civitas.util.Use;

class VerifyVerifiableVoteTest extends TestBase
		implements VerifiableVoteTestData {
	@Tested
	VerifyVerifiableVote verifyVerifiableVote;

	@Use
	VerifyVerifiableVote verifyVerifiableVoteReal;

	@Test
	@DisplayName("if the vote is correct, returns true\n"
			+ "the ciphertexts in the vote are 1 of L reencryption for \n"
			+ "the encrypted choice in the vote and the public key, and\n"
			+ "the vote proof in the vote is correct for the encrypted capability,\n"
			+ "and the encrypted choice using the context and the el gamal parameters of the public key")
	void test() {
		assertTrue(verifyVerifiableVote.apply(VERIFIABLE_VOTE,
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

	@Test
	@Tag("testdata")
	@DisplayName("VERIFIABLE_VOTES")
	void test1() {
		for (VerifiableVote vv : VERIFIABLE_VOTES)
			assertTrue(verifyVerifiableVoteReal.apply(vv, EL_GAMAL_PUBLIC_KEY_E,
					CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS));
	}

}
