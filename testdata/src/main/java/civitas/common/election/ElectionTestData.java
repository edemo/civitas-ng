package civitas.common.election;

import civitas.common.UtilTestData;
import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.common.electionresults.TellerTestData;
import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public interface ElectionTestData extends BallotDesignTestData,
		ElGamalParametersTestData, PublicKeyTestData, UtilTestData, TellerTestData {

	String ELECTION_ID_STRING = "The Greatest Election ID";
	String ELECTION_URI_BASE = "https://voting.demokracia.rulez.org:4420/voting";
	ElectionID ELECTION_ID = new ElectionID(ELECTION_ID_STRING,
			ELECTION_URI_BASE);
	String ELECTION_NAME = "The Greatest Election name";
	String ELECTION_DESCRIPTION = "This is a great election, I have never seen better election than this one";
	String START_TIME = "staring time";
	String STOP_TIME = "the time when you stop running is the time when you arrive";
	String FINALIZE_TIME = "nothing is final, ever";
	int NONCE_LENGTH = BITLENGTH;
	int BLOCKSIZE = 400;

	ElectionDetails ELECTION_DETAILS = new ElectionDetails(ELECTION_ID.id,
			PUBLIC_KEY2_BASE64, PUBLIC_KEY_BASE64, ELECTION_NAME,
			ELECTION_DESCRIPTION, VERSIONSTRING, BALLOTDESIGN, START_TIME, STOP_TIME,
			FINALIZE_TIME, EL_GAMAL_PARAMETERS.p.i, EL_GAMAL_PARAMETERS.q.i,
			EL_GAMAL_PARAMETERS.g.i, KEYSIZE, NONCE_LENGTH, BLOCKSIZE);

	String BLOCKNAME_14 = "voterBlock-4-context-" + BARE_CONTEXT_2;
	String FULL_CONTEXT_11 = ELECTION_ID_STRING + ":3:" + BARE_CONTEXT_2;
	String FULL_CONTEXT_14 = ELECTION_ID_STRING + ":4:" + BARE_CONTEXT_2;

	String ABANDONMENT_REASON = "just why not";
	ElectionAbandonment ELECTION_ABANDONMENT = new ElectionAbandonment(
			TELLER_INDEX, true, ABANDONMENT_REASON);
	ElectionAbandonment ELECTION_ABANDONMENT_NONTELLER = new ElectionAbandonment(
			TELLER_INDEX, false, ABANDONMENT_REASON);
	String ELECTION_ABANDONMENT_REPORTER = "tabulation teller " + TELLER_INDEX;
	String ELECTION_ABANDONMENT_REPORTER_UNKNOWN = "unknown entity";

}
