package civitas.common;

import java.util.Map;

public interface CommonConstants {
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

	int MAX_POSSIBLE_CHOICES = 4;

	int VOTE_CHOICE_I_BEATS_J = 1;
	int VOTE_CHOICE_J_BEATS_I = 2;
	int VOTE_CHOICE_NEITHER_BEAT = 3;

	String VOTE_CHOICE_I_BEATS_J_STRING = "10";
	String VOTE_CHOICE_J_BEATS_I_STRING = "01";
	String VOTE_CHOICE_NEITHER_BEAT_STRING = "00";

	Map<String, Integer> STRING_TO_CHOICE_MAP = Map.of(
			VOTE_CHOICE_I_BEATS_J_STRING, VOTE_CHOICE_I_BEATS_J,
			VOTE_CHOICE_J_BEATS_I_STRING, VOTE_CHOICE_J_BEATS_I,
			VOTE_CHOICE_NEITHER_BEAT_STRING, VOTE_CHOICE_NEITHER_BEAT);

}
