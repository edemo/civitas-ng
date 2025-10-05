package civitas.common;

public interface CommonConstants {

	int ELECTION_ID_LENGTH = 32;

	String BOARD_CLOSED_CONTENT_COMMITMENT_META = "boardContents";

	String ELECTION_DETAILS_META = "electiondetails";
	String ELECTION_EVENT_META = "electionevent";
	String CIPHERTEXT_LIST_META = "ciphertextList";

	String CONDORCET_BALLOT_OPENING_TAG = "ballot";
	String BALLOT_DESIGN_OPENING_TAG = "ballotDesign";
	String VERIFIABLE_VOTE_OPENING_TAG = "verifiableVote";
	String TALLY_STATE_FINAL_OPENING_TAG = "tallyState";

	String ELEC_ROLL = "elecRoll";
	String CAPABILITY_MIX_META = "capabilityMix:";
	String ELECTION_RESULTS_META = "electionResults:";
	String ELECTORAL_ROLL_CAPABILITY_SHARES_META = "electoralRollCapShares";
	String MIX_CONFIRM_META_PREFIX = "mixConfirm:";
	String MIX_CONFIRM_META_VOTE_REVELATION = "vote";
	String MIX_CONFIRM_META_ER_REVELATION = ELEC_ROLL;
	String MIX_HASH_REVELATION_META_PREFIX = "mixHashRevelation:";
	String MIX_HASH_REVELATION_META_VOTE_REVELATION = "vote";
	String MIX_HASH_REVELATION_META_ER_REVELATION = ELEC_ROLL;
	String VOTE_MIX_META = "voteMix:";
	String MIX_REVELATION_META_PREFIX = "mixRevelation:";
	String MIX_REVELATION_META_VOTE_REVELATION = "vote";
	String MIX_REVELATION_META_ER_REVELATION = ELEC_ROLL;

	String KIND = "condorcet";
	String NONE_OF_ABOVE = "none of the above";
}
