package civitas.crypto.proofvote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.util.Use;

public class ProofVoteCTest extends TestBase
		implements ProofVoteTestData, ElGamalCiphertextCTestData {

	@Use
	ProofVoteFromXML proofVoteFromXML;
	@Use
	ProofVoteToXML proofVoteToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PROOF_VOTE_XML, proofVoteToXML.apply(PROOF_VOTE));
	}

	@Test
	@DisplayName("equals and fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(PROOF_VOTE
				.equals(proofVoteFromXML.apply(new StringReader(PROOF_VOTE_XML))));
	}

	@Test
	@DisplayName("does not equal anything not of type ProofVoteC")
	void test5() {

		assertFalse(PROOF_VOTE.equals(mock(ProofVote.class)));
	}

	@Test
	@DisplayName("not equals if c does not equals")
	void test6() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVote(BIGINT_P, PROOF_VOTE_S1, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s1 does not equals")
	void test6_1() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVote(PROOF_VOTE_C, BIGINT_P, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s2 does not equals")
	void test6_2() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVote(PROOF_VOTE_C, PROOF_VOTE_S1, BIGINT_P)));
	}

}
