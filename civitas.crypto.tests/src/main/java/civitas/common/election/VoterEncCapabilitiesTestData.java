package civitas.common.election;

import civitas.common.ballot.BallotTestData;
import civitas.common.capabilityencryption.VoterEncCapabilities;
import civitas.crypto.ciphertext.ElGamalCiphertext;

public interface VoterEncCapabilitiesTestData extends BallotTestData {

	VoterEncCapabilities VOTER_ENC_CAPABILITIES = new VoterEncCapabilities("joe",
			VOTER_BLOCK, new ElGamalCiphertext[0]);

}
