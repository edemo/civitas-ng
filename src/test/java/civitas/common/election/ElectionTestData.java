package civitas.common.election;

import civitas.common.ElectionAbandonment;
import civitas.common.ElectionID;
import civitas.common.UtilTestData;
import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public interface ElectionTestData extends BallotDesignTestData,
		ElGamalParametersTestData, PublicKeyTestData, UtilTestData {

	String ELECTION_ID_STRING = "The Greatest Election ID";
	int ELECTION_PORT = 4420;
	String ELECTION_HOST = "voting.demokracia.rulez.org";
	String ELECTION_ID_AS_STRING = ELECTION_HOST + ":" + ELECTION_PORT + ":"
			+ ELECTION_ID_STRING;
	ElectionID ELECTION_ID = new ElectionID(ELECTION_HOST, ELECTION_PORT,
			ELECTION_ID_STRING);
	String ELECTION_NAME = "The Greatest Election name";
	String ELECTION_DESCRIPTION = "This is a great election, I have never seen better election than this one";
	String START_TIME = "staring time";
	String STOP_TIME = "the time when you stop running is the time when you arrive";
	String FINALIZE_TIME = "nothing is final, ever";
	int NONCE_LENGTH = BITLENGTH;
	int BLOCKSIZE = 400;

	ElectionDetails ELECTION_DETAILS = new ElectionDetails(ELECTION_ID,
			PUBLIC_KEY2, PUBLIC_KEY, ELECTION_NAME, ELECTION_DESCRIPTION,
			VERSIONSTRING, BALLOTDESIGN, START_TIME, STOP_TIME, FINALIZE_TIME,
			EL_GAMAL_PARAMETERS, KEYSIZE, NONCE_LENGTH, BLOCKSIZE);

	String BLOCKNAME_14 = "voterBlock-4-context-" + BARE_CONTEXT_2;
	String FULL_CONTEXT_11 = ELECTION_ID_AS_STRING + ":3:" + BARE_CONTEXT_2;
	String FULL_CONTEXT_14 = ELECTION_ID_AS_STRING + ":4:" + BARE_CONTEXT_2;

	int TELLER_INDEX = 1;
	String ABANDONMENT_REASON = "just why not";
	ElectionAbandonment ELECTION_ABANDONMENT = new ElectionAbandonment(
			TELLER_INDEX, true, ABANDONMENT_REASON);
	ElectionAbandonment ELECTION_ABANDONMENT_NONTELLER = new ElectionAbandonment(
			TELLER_INDEX, false, ABANDONMENT_REASON);
	String ELECTION_ABANDONMENT_REPORTER = "tabulation teller 1";
	String ELECTION_ABANDONMENT_REPORTER_UNKNOWN = "unknown entity";

}
