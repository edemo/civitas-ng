package civitas.common.ballot;

import civitas.common.CommonConstants;

public interface BallotTestData extends CommonConstants {

	Ballot BALLOT_EMPTY = new Ballot(3, new int[3]);

	Ballot BALLOT_ONE_RECORD = new Ballot(3,
			new int[] { 0, VOTE_CHOICE_I_BEATS_J, 0 });

	int VOTER_BLOCK = 3;
	Ballot BALLOT = new Ballot(VOTER_BLOCK,
			new int[] {
					VOTE_CHOICE_I_BEATS_J,
					VOTE_CHOICE_J_BEATS_I,
					VOTE_CHOICE_NEITHER_BEAT });

	Ballot BALLOT_SHORT_MATRIX = new Ballot(3,
			new int[] { VOTE_CHOICE_I_BEATS_J, VOTE_CHOICE_J_BEATS_I });

	Ballot BALLOT_2_LENGTH = new Ballot(2, new int[] { VOTE_CHOICE_I_BEATS_J });

	//@formatter:off
	String BALLOT_XML = "<ballot><k>3</k><matrix>"
			+ "<entry><i>0</i><j>1</j><choice>" 
			+ VOTE_CHOICE_I_BEATS_J_STRING
			+ "</choice></entry>"
			+ "<entry><i>0</i><j>2</j><choice>"
			+ VOTE_CHOICE_J_BEATS_I_STRING 
			+ "</choice></entry>"
			+ "<entry><i>1</i><j>2</j><choice>" 
			+ VOTE_CHOICE_NEITHER_BEAT_STRING
			+ "</choice></entry>" + "</matrix></ballot>";
	String BALLOT_XML_BAD_PAIR = "<ballot><k>3</k><matrix>"
			+ "<entry><i>0</i><j>2</j><choice>"
			+ VOTE_CHOICE_J_BEATS_I_STRING 
			+ "</choice></entry>"
			+ "<entry><i>1</i><j>2</j><choice>" 
			+ VOTE_CHOICE_NEITHER_BEAT_STRING
			+ "</choice></entry>" + "</matrix></ballot>";
	String BALLOT_XML_BAD_PAIR2 = "<ballot><k>3</k><matrix>"
			+ "<entry><i>1</i><j>2</j><choice>"
			+ VOTE_CHOICE_J_BEATS_I_STRING 
			+ "</choice></entry>"
			+ "<entry><i>1</i><j>2</j><choice>" 
			+ VOTE_CHOICE_NEITHER_BEAT_STRING
			+ "</choice></entry>" + "</matrix></ballot>";
	//@formatter:on

}
