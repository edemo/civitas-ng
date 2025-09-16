package civitas.common.election;

import civitas.common.UtilTestData;
import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public interface ElectionDetailsTestData
		extends ElectionIdTestData, PublicKeyTestData, BallotDesignTestData,
		ElGamalParametersTestData, UtilTestData {

	String ELECTION_NAME = "The Greatest Election name";
	String ELECTION_DESCRIPTION = "This is a great election, I have never seen better election than this one";
	String START_TIME = "staring time";
	String STOP_TIME = "the time when you stop running is the time when you arrive";
	String FINALIZE_TIME = "nothing is final, ever";
	int NONCE_LENGTH = 64;
	int BLOCKSIZE = 400;

	ElectionDetails ELECTION_DETAILS = new ElectionDetails(ELECTION_ID.id(),
			PUBLIC_KEY2_BASE64, PUBLIC_KEY_BASE64, ELECTION_NAME,
			ELECTION_DESCRIPTION, VERSIONSTRING, BALLOTDESIGN, START_TIME, STOP_TIME,
			FINALIZE_TIME, EL_GAMAL_PARAMETERS.p().i, EL_GAMAL_PARAMETERS.q().i,
			EL_GAMAL_PARAMETERS.g().i, KEYSIZE, NONCE_LENGTH, BLOCKSIZE);

}
