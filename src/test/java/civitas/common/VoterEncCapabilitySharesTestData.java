package civitas.common;

import civitas.common.ballot.BallotTestData;
import civitas.common.board.BulletinBoardTestData;
import civitas.common.electionresults.TellerTestData;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;

public interface VoterEncCapabilitySharesTestData extends TellerTestData,
		BulletinBoardTestData, ElGamalCiphertextTestData, BallotTestData {
	VoterEncCapabilityShares VOTER_ENC_CAPABILITY_SHARE = new VoterEncCapabilityShares(
			TELLER_INDEX, BULLETIN_BOARD_ID, VOTER_BLOCK, POSTED_CAPABILITIES);
	VoterEncCapabilityShares[] VOTER_ENC_CAPABILITY_SHARES = new VoterEncCapabilityShares[] {
			VOTER_ENC_CAPABILITY_SHARE };

}
