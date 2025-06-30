package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.capabilityencryption.VoterEncCapabilities;
import civitas.common.votersubmission.VoterSubmission;
import civitas.common.votersubmission.VoterSubmissionTestData;

class IsVoterSubmissionInBlockTest extends TestBase implements ElectionTestData,
		VoterSubmissionTestData, VoterEncCapabilitiesTestData {

	@InjectMocks
	IsVoterSubmissionInBlock isVoterSubmissionInBlock;

	@Test
	@DisplayName("checks if the block in the voter submission correponds to the block number given\n"
			+ "(version without context)")
	void test() {
		assertTrue(
				isVoterSubmissionInBlock.apply(ELECTION_DETAILS, VOTER_SUBMISSION, 11));
	}

	@Test
	@DisplayName("returns false if not\n" + "(version without context)")
	void test1() {
		assertFalse(
				isVoterSubmissionInBlock.apply(ELECTION_DETAILS, VOTER_SUBMISSION, 14));
	}

	@Test
	@DisplayName("if voter is null, throws an IllegalArgumentException\n"
			+ "(version without context)")
	void test2() {
		assertThrows(IllegalArgumentException.class,
				() -> isVoterSubmissionInBlock.apply(ELECTION_DETAILS, null, 11));
	}

	@Test
	@DisplayName("checks if the block in the voter submission correponds to the block number and context given\n"
			+ "(version with context)")
	void test_1() {
		assertTrue(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_SUBMISSION, FULL_CONTEXT_11, 11));
	}

	@Test
	@DisplayName("returns false if context is different\n"
			+ "(version with context)")
	void test3_1() {
		assertFalse(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_SUBMISSION, FULL_CONTEXT_14, 11));
	}

	@Test
	@DisplayName("returns false if block number is different\n"
			+ "(version with context)")
	void test1_1() {
		assertFalse(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_SUBMISSION, FULL_CONTEXT_14, 14));
	}

	@Test
	@DisplayName("if voter is null, throws an IllegalArgumentException\n"
			+ "(version with context)")
	void test2_1() {
		assertThrows(IllegalArgumentException.class, () -> isVoterSubmissionInBlock
				.apply(ELECTION_DETAILS, (VoterSubmission) null, FULL_CONTEXT_11, 11));
	}

	@Test
	@DisplayName("checks if the block in the encrypted capabilities correponds to the block number and context given\n"
			+ "(version with encrypted capabilities)")
	void test_2() {
		assertTrue(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_ENC_CAPABILITIES, BARE_CONTEXT_2, 11));
	}

	@Test
	@DisplayName("returns false if context is different\n"
			+ "(version with encrypted capabilities)")
	void test3_2() {
		assertFalse(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_ENC_CAPABILITIES, BARE_CONTEXT_1, 11));
	}

	@Test
	@DisplayName("returns false if block number is different\n"
			+ "(version with encrypted capabilities)")
	void test1_2() {
		assertFalse(isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
				VOTER_ENC_CAPABILITIES, BARE_CONTEXT_2, 14));
	}

	@Test
	@DisplayName("if voter is null, throws an IllegalArgumentException\n"
			+ "(version with encrypted capabilities)")
	void test2_2() {
		assertThrows(IllegalArgumentException.class,
				() -> isVoterSubmissionInBlock.apply(ELECTION_DETAILS,
						(VoterEncCapabilities) null, BARE_CONTEXT_2, 11));
	}

}
