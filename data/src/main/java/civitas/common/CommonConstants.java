package civitas.common;

public interface CommonConstants {

	int ELECTION_ID_LENGTH = 32;

	String BoardClosedContentCommitmentMETA = "boardContents";

	String ElectionDetailsMETA = "electiondetails";
	String ElectionEventMETA = "electionevent";
	String CiphertextListMETA = "ciphertextList";

	String CondorcetBallotOPENING_TAG = "ballot";
	String BallotDesignOPENING_TAG = "ballotDesign";
	String VerifiableVoteOPENING_TAG = "verifiableVote";
	String TallyStateFinalOPENING_TAG = "tallyState";

	String CapabilityMixMETA = "capabilityMix:";
	String ElectionResultsMETA = "electionResults:";
	String ElectoralRollCapabilitySharesMETA = "electoralRollCapShares";
	String mixConfirmMETA_PREFIX = "mixConfirm:";
	String mixConfirmMETA_VOTE_REVELATION = "vote";
	String mixConfirmMETA_ER_REVELATION = "elecRoll";
	String mixHashRevelationMETA_PREFIX = "mixHashRevelation:";
	String mixHashRevelationMETA_VOTE_REVELATION = "vote";
	String mixHashRevelationMETA_ER_REVELATION = "elecRoll";
	String VoteMixMETA = "voteMix:";
	String mixRevelationMETA_PREFIX = "mixRevelation:";
	String mixRevelationMETA_VOTE_REVELATION = "vote";
	String mixRevelationMETA_ER_REVELATION = "elecRoll";

	String KIND = "condorcet";
	String NONE_OF_ABOVE = "none of the above";

}
