package civitas.common;

import civitas.common.ballot.BallotTestData;
import civitas.common.board.BulletinBoardTestData;
import civitas.common.electionresults.TellerTestData;
import civitas.crypto.ciphertextlist.ElGamalCiphertextListTestData;

public interface VoterEncCapabilitySharesTestData
		extends TellerTestData, BulletinBoardTestData, ElGamalCiphertextListTestData, BallotTestData {
	VoterEncCapabilityShares VOTER_ENC_CAPABILITY_SHARE =
			new VoterEncCapabilityShares(TELLER_INDEX, BULLETIN_BOARD_ID, VOTER_BLOCK, POSTED_CAPABILITIES);
	VoterEncCapabilityShares[] VOTER_ENC_CAPABILITY_SHARES = {VOTER_ENC_CAPABILITY_SHARE};
}
