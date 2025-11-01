package civitas.common.tests;

import civitas.common.VoterEncCapabilityShares;
import civitas.common.ballot.tests.BallotTestData;
import civitas.common.board.tests.BulletinBoardTestData;
import civitas.common.electionresults.tests.TellerTestData;
import civitas.crypto.ciphertextlist.tests.ElGamalCiphertextListTestData;

public interface VoterEncCapabilitySharesTestData
		extends TellerTestData, BulletinBoardTestData, ElGamalCiphertextListTestData, BallotTestData {
	VoterEncCapabilityShares VOTER_ENC_CAPABILITY_SHARE =
			new VoterEncCapabilityShares(TELLER_INDEX, BULLETIN_BOARD_ID, VOTER_BLOCK, POSTED_CAPABILITIES);
	VoterEncCapabilityShares[] VOTER_ENC_CAPABILITY_SHARES = {VOTER_ENC_CAPABILITY_SHARE};
}
