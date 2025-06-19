package civitas.crypto.proofknowndisclog;

import civitas.common.Util;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.util.CivitasBigInteger;

public interface ProofKnowDiscLogTestData extends ElGamalPublicKeyTestData {

	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_A = BIGINT_G.modPow(RANDOMS_0,
			BIGINT_P);
	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_V = BIGINT_G.modPow(PRIVKEY_E,
			BIGINT_P);
	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_C = Util
			.asBigint("V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=");

	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_R = RANDOMS_0.modAdd(
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_C.modMultiply(PRIVKEY_E, BIGINT_Q),
			BIGINT_Q);

	ElGamalProofKnowDiscLog EL_GAMAL_PROOF_KNOWN_DISC_LOG = new ElGamalProofKnowDiscLog(
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_A, EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, EL_GAMAL_PROOF_KNOWN_DISC_LOG_V);
	ElGamalProofKnowDiscLog EL_GAMAL_PROOF_KNOWN_DISC_LOG_BAD = new ElGamalProofKnowDiscLog(
			BIGINT_A, EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, EL_GAMAL_PROOF_KNOWN_DISC_LOG_V);

}
