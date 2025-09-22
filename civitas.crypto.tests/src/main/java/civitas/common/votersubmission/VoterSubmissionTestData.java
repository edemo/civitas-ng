package civitas.common.votersubmission;

import civitas.common.capabilityencryption.CapabilityEncryption;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifiableVoteTestData;

public interface VoterSubmissionTestData extends VerifiableVoteTestData {

	CapabilityEncryption ENCRYPT_CAPABILITY_RESULT = new CapabilityEncryption(
			ELGAMAL_REENCRYPT_FACTOR_EPRIME,
			ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME.getFirst());

	VoterSubmission VOTER_SUBMISSION = new VoterSubmission(VOTER_BLOCK,
			VERIFIABLE_VOTES);

	VoterSubmission VOTER_SUBMISSION_WITH_NULL = new VoterSubmission(VOTER_BLOCK,
			new VerifiableVote[] { null });
	VoterSubmission VOTER_SUBMISSION_WITH_BAD_CONTEXT = new VoterSubmission(
			VOTER_BLOCK, new VerifiableVote[] { VERIFIABLE_VOTES[1] });

	VoterSubmission VOTER_SUBMISSION_WITH_BAD_PROOF = new VoterSubmission(
			VOTER_BLOCK,
			new VerifiableVote[] {
					VERIFIABLE_VOTES[0],
					new VerifiableVote(VERIFIABLE_VOTES[1].context(),
							VERIFIABLE_VOTES[1].encChoice(), VERIFIABLE_VOTES[1].encCapability(),
							PROOF_VOTE_MAP.get(0)),
					VERIFIABLE_VOTES[2] });

}
