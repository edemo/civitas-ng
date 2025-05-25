package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ProofVote;
import civitas.crypto.algorithms.ProofVoteFromXML;
import civitas.util.Use;

public class ProofVoteCTest extends ConcreteTestBase
		implements ProofVoteTestData, ElGamalCiphertextCTestData {

	@Use
	ProofVoteFromXML proofVoteFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PROOF_VOTE_XML, PROOF_VOTE.toXML());
	}

	@Test
	@DisplayName("equals and fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(PROOF_VOTE
				.equals(proofVoteFromXML.apply(new StringReader(PROOF_VOTE_XML))));
	}

	@Test
	@DisplayName("null ProofVote does not equal itself")
	void test4() {
		assertFalse(new ProofVoteC(null, null, null)
				.equals(new ProofVoteC(null, null, null)));
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
				.equals(new ProofVoteC(BIGINT_P, PROOF_VOTE_S1, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s1 does not equals")
	void test6_1() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVoteC(PROOF_VOTE_C, BIGINT_P, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s2 does not equals")
	void test6_2() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVoteC(PROOF_VOTE_C, PROOF_VOTE_S1, BIGINT_P)));
	}

	@Test
	@DisplayName("constructor accepts nulls")
	void test1() {
		assertEquals(PROOF_VOTE_NULL_XML, new ProofVoteC(null, null, null).toXML());
	}

}
