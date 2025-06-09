package civitas.common;

import java.util.Map;

public interface CommonConstants {
	public final static String CondorcetBallotOPENING_TAG = "ballot";
	public final static String BallotDesignOPENING_TAG = "ballotDesign";
	public static final String VerifiableVoteOPENING_TAG = "verifiableVote";
	public final static String TallyStateFinalOPENING_TAG = "tallyState";

	String CapabilityMixMETA = "capabilityMix:";
	String ElectionResultsMETA = "electionResults:";
	String ElectoralRollCapabilitySharesMETA = "electoralRollCapShares";

	public static final String KIND = "condorcet";
	String NONE_OF_ABOVE = "none of the above";

	public int MAX_POSSIBLE_CHOICES = 4;

	public static final int VOTE_CHOICE_I_BEATS_J = 1;
	public static final int VOTE_CHOICE_J_BEATS_I = 2;
	public static final int VOTE_CHOICE_NEITHER_BEAT = 3;

	String VOTE_CHOICE_I_BEATS_J_STRING = "10";
	String VOTE_CHOICE_J_BEATS_I_STRING = "01";
	String VOTE_CHOICE_NEITHER_BEAT_STRING = "00";

	Map<String, Integer> STRING_TO_CHOICE_MAP = Map.of(
			VOTE_CHOICE_I_BEATS_J_STRING, VOTE_CHOICE_I_BEATS_J,
			VOTE_CHOICE_J_BEATS_I_STRING, VOTE_CHOICE_J_BEATS_I,
			VOTE_CHOICE_NEITHER_BEAT_STRING, VOTE_CHOICE_NEITHER_BEAT);

}
