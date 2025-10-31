package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.common.ballotdesign.CalculateBallotLength;
import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.common.ballotdesign.DecomposeBallot;
import civitas.common.capabilityencryption.EncryptCapability;
import civitas.common.encryptedchoice.EncryptChoice;
import civitas.common.tests.RandomAwareTestBase;
import civitas.common.votersubmission.VoterSubmission;
import civitas.common.votersubmission.tests.VoterSubmissionTestData;
import civitas.crypto.oneoflreencryption.tests.ElGamal1OfLReencryptionTestData;
import civitas.crypto.proofvote.ConstructProofVote;

class DecomposeBallotTest extends RandomAwareTestBase
		implements VoterSubmissionTestData, ElGamal1OfLReencryptionTestData {

	@InjectMocks
	DecomposeBallot decomposeBallot;

	@Mock
	EncryptCapability encryptCapability;

	@Mock
	EncryptChoice encryptChoice;

	@Mock
	CalculatePositionInBallot calculatePositionInBallot;

	@Mock
	ConstructProofVote constructProofVote;

	@Mock
	CalculateBallotLength calculateBallotLength;

	@Test
	@DisplayName(
			"""
			Decomposes a ballot into a VoterSubmission
			- calculates the length of the ballot based on the number of candidates
			- for each pair i,j:
			- calculates their position in the matrix
			- computes the encrypted choice by constructing an 1 of L reeencryption
			- encrypts the capability from the map
			- constructs a vote proof using all the above
			- creates a verifiable vote using the context, the encryted capability and choice and the proof
			- returns a voter submission for the voter block with the verifiable votes
			""")
	void test() {
		VoterSubmission actual = decomposeBallot.apply(
				BALLOTDESIGN,
				BALLOT,
				VOTER_BLOCK,
				EL_GAMAL_PUBLIC_KEY_E,
				CIPHERTEXT_LIST,
				ADDITIONALENV,
				CAPABILITY_MAP);
		assertEquals(VOTER_SUBMISSION, actual);
		verify(calculateBallotLength).apply(BALLOT.k);
		CalculatePositionInBallot posStub = CalculatePositionInBallotStub.stub();
		for (int i = 0; i < NUM_CANDIDATES; i++) {
			for (int j = i + 1; j < NUM_CANDIDATES; j++) {
				Integer pos = posStub.apply(i, j, NUM_CANDIDATES);
				verify(encryptCapability).apply(EL_GAMAL_PUBLIC_KEY_E, CAPABILITY_MAP, CONTEXT_0);
				verify(encryptChoice).apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, BALLOT.matrix, pos);
				verify(calculatePositionInBallot).apply(i, j, NUM_CANDIDATES);

				verify(constructProofVote)
						.apply(
								EL_GAMAL_PARAMETERS,
								ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(pos),
								EL_GAMAL_1_OF_L_REENCRYPTION_MAP
										.get(BALLOT.matrix[pos])
										.m(),
								CONTEXT_MAP.get(pos),
								ELGAMAL_REENCRYPT_FACTOR_E,
								ELGAMAL_REENCRYPT_FACTOR_EPRIME);
			}
		}
	}

	@Test
	@DisplayName("if the key is null, IllegalArgumentException is thrown")
	void test1() {
		assertThrows(
				IllegalArgumentException.class,
				() -> decomposeBallot.apply(
						BALLOTDESIGN, BALLOT, VOTER_BLOCK, null, CIPHERTEXT_LIST, ADDITIONALENV, CAPABILITY_MAP));
	}

	@Test
	@DisplayName("if the number of candidates in the ballot does not match the number of candidates,"
			+ "IllegalArgumentException is thrown")
	void test2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> decomposeBallot.apply(
						BALLOTDESIGN,
						BALLOT_2_CANDIDATES,
						VOTER_BLOCK,
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						ADDITIONALENV,
						CAPABILITY_MAP));
	}

	@Test
	@DisplayName("if the matrix length in the ballot does not match the number of candidates,"
			+ "IllegalArgumentException is thrown")
	void test3() {
		assertThrows(
				IllegalArgumentException.class,
				() -> decomposeBallot.apply(
						BALLOTDESIGN,
						BALLOT_SHORT_MATRIX,
						VOTER_BLOCK,
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						ADDITIONALENV,
						CAPABILITY_MAP));
	}
}
