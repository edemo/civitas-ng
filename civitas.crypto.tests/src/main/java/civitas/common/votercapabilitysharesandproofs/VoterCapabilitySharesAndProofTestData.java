package civitas.common.votercapabilitysharesandproofs;

import civitas.common.ballot.BallotTestData;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.proofdvr.ElGamalProofDVRTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;

public interface VoterCapabilitySharesAndProofTestData
		extends VoteCapabilityShareTestData, ElGamalProofDVRTestData, BallotTestData {
	VoterCapabilitySharesAndProof VOTER_CAPABILITIES_AND_PROOFS =
			new VoterCapabilitySharesAndProof(VOTE_CAPABILITY_SHARES, FACTORS, PROOFS, VOTER_BLOCK);
	VoterCapabilitySharesAndProof VOTER_CAPABILITIES_AND_PROOFS_CAP_NONVERIFY =
			new VoterCapabilitySharesAndProof(VOTE_CAPABILITY_SHARES, FACTORS, PROOFS_CAP_NONVERIFY, VOTER_BLOCK);
	VoterCapabilitySharesAndProof VOTER_CAPABILITIES_AND_PROOFS_BAD_FACTOR_COUNTS = new VoterCapabilitySharesAndProof(
			VOTE_CAPABILITY_SHARES, new ElGamalReencryptFactor[0], PROOFS, VOTER_BLOCK);
	VoterCapabilitySharesAndProof VOTER_CAPABILITIES_AND_PROOFS_BAD_PROOF_COUNT =
			new VoterCapabilitySharesAndProof(VOTE_CAPABILITY_SHARES, FACTORS, new ElGamalProofDVR[0], VOTER_BLOCK);
}
