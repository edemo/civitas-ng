package civitas.crypto.proofknowndisclog;

import civitas.common.Util;
import civitas.crypto.publickey.ElGamalPublicKeyCTestData;
import civitas.util.CivitasBigInteger;

public interface ProofKnowDiscLogTestData extends ElGamalPublicKeyCTestData {
	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_A = BIGINT_G.modPow(RANDOMS_0,
			BIGINT_P);
	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_V = BIGINT_G.modPow(PRIVKEY_E,
			BIGINT_P);
	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 = "V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=";
	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_C = Util
			.asBigint(EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64);

	CivitasBigInteger EL_GAMAL_PROOF_KNOWN_DISC_LOG_R = RANDOMS_0.modAdd(
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_C.modMultiply(PRIVKEY_E, BIGINT_Q),
			BIGINT_Q);

	ElGamalProofKnowDiscLog EL_GAMAL_PROOF_KNOWN_DISC_LOG = new ElGamalProofKnowDiscLog(
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_A, EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, EL_GAMAL_PROOF_KNOWN_DISC_LOG_V);
	ElGamalProofKnowDiscLog EL_GAMAL_PROOF_KNOWN_DISC_LOG_BAD = new ElGamalProofKnowDiscLog(
			BIGINT_A, EL_GAMAL_PROOF_KNOWN_DISC_LOG_C,
			EL_GAMAL_PROOF_KNOWN_DISC_LOG_R, EL_GAMAL_PROOF_KNOWN_DISC_LOG_V);

	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 = Util
			.fromBigInt(EL_GAMAL_PROOF_KNOWN_DISC_LOG_A);
	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 = Util
			.fromBigInt(EL_GAMAL_PROOF_KNOWN_DISC_LOG_R);
	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_V_BASE64 = Util
			.fromBigInt(EL_GAMAL_PROOF_KNOWN_DISC_LOG_V);

	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_XML = "<elGamalProofKnowDiscLog><a>"
			+ EL_GAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 + "</a><c>"
			+ EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 + "</c><r>"
			+ EL_GAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 + "</r><v>"
			+ EL_GAMAL_PROOF_KNOWN_DISC_LOG_V_BASE64
			+ "</v></elGamalProofKnowDiscLog>";
	public static final String EL_GAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML = "<elGamalProofKnowDiscLog><a>"
			+ "</a><c>" + "</c><r>" + "</r><v>" + "</v></elGamalProofKnowDiscLog>";

}
