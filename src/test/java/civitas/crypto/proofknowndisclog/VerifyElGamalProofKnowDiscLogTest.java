package civitas.crypto.proofknowndisclog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyElGamalProofKnowDiscLogTest extends ConcreteTestBase
		implements ProofKnowDiscLogTestData {
	@Tested
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;
	@Use
	CryptoHash cryptoHash;

	@Test
	@DisplayName("verify checks that g^r = av^c (mod p)")
	void verifyTest() {

		CivitasBigInteger key = ELGAMAL_PRIVATE_KEY_E.x;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;

		CivitasBigInteger v = g.modPow(key, p);
		CivitasBigInteger z = RANDOMS_0;
		CivitasBigInteger a = g.modPow(z, p);
		CivitasBigInteger c = cryptoHash.apply(v, a).mod(q);
		CivitasBigInteger r = z.add(c.modMultiply(key, q));
		assertTrue(verifyElGamalProofKnowDiscLog
				.apply(new ElGamalProofKnowDiscLog(a, c, r, v), EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify fails if parameters are not of type ElGamalParametersC")
	void verifyTest1() {
		assertFalse(
				verifyElGamalProofKnowDiscLog.apply(
						new ElGamalProofKnowDiscLog(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A,
								EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
								EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, G_EXP_A),
						mock(ElGamalParameters.class)));
	}

}
