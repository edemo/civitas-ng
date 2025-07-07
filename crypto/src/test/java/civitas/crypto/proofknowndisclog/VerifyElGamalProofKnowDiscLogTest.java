package civitas.crypto.proofknowndisclog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigIntegerFactory;

public class VerifyElGamalProofKnowDiscLogTest extends TestBase
		implements ProofKnowDiscLogTestData {
	@InjectMocks
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;

	@Test
	@DisplayName("verify checks that g^r = av^c (mod p)")
	void verifyTest() {

		assertTrue(verifyElGamalProofKnowDiscLog
				.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify returns false if the check fails)")
	void verifyTest1_1() {

		assertFalse(verifyElGamalProofKnowDiscLog
				.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG_BAD, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify fails if the proof or the parameters have a null attribute")
	void verifyTest1() {
		assertFalse(
				verifyElGamalProofKnowDiscLog.apply(
						new ElGamalProofKnowDiscLog(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A,
								EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
								EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, G_EXP_A),
						mock(ElGamalParameters.class)));
	}

	@Test
	@DisplayName("verify fails if numbers are so off tha the arithmetics does not works")
	void verifyTest2() {
		assertFalse(verifyElGamalProofKnowDiscLog.apply(
				new ElGamalProofKnowDiscLog(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A,
						EL_GAMAL_PROOF_KNOWN_DISC_LOG_C, EL_GAMAL_PROOF_KNOWN_DISC_LOG_R,
						G_EXP_A),
				new ElGamalParameters(CivitasBigIntegerFactory.obtain(-1), BIGINT_Q,
						BIGINT_G)));
	}

}
