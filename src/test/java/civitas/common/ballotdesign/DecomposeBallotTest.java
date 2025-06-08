package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.votersubmission.VoterSubmission;
import civitas.common.votersubmission.VoterSubmissionTestData;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.util.Tested;
import civitas.util.Use;

class DecomposeBallotTest extends TestBase implements VoterSubmissionTestData {

	@Tested
	DecomposeBallot decomposeBallot;

	@Use
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("Decomposes a ballot into a VoterSubmission")
	void test() {
		VoterSubmission actual = decomposeBallot.apply(BALLOTDESIGN, BALLOT,
				VOTER_BLOCK, EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, ADDITIONALENV,
				CAPABILITY_MAP);

		assertEquals(VOTER_SUBMISSION, actual);
	}

}
