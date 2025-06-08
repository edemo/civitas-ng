package civitas.crypto.proofdvr;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalProofDVRTestData extends ElGamalCiphertextTestData {

	String DVR_HASH_BASE64 = "AJjM7UWkhFgdnqy5yiDJJMUKuLDFD/k8Yqv74UN9Qhp6";
	CivitasBigInteger DVR_HASH = Util.asBigint(DVR_HASH_BASE64);

	CivitasBigInteger DVR_U = RANDOMS_0.modAdd(
			ZETA.modMultiply(DVR_HASH.modAdd(RANDOMS_1, BIGINT_Q), BIGINT_Q),
			BIGINT_Q);
	String DVR_U_BASE64 = Util.fromBigInt(DVR_U);
	ElGamalProofDVR EL_GAMAL_PROOF_DVR = new ElGamalProofDVR(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, DVR_HASH, RANDOMS_1, RANDOMS_2, DVR_U);

	public static final String EL_GAMAL_PROOF_DVR_XML = "<elGamalProofDVR>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_CIPHERTEXT_EPRIME_XML + "<c>"
			+ DVR_HASH_BASE64 + "</c><w>" + RANDOMS_1_BASE64 + "</w><r>"
			+ RANDOMS_2_BASE64 + "</r><u>" + DVR_U_BASE64 + "</u></elGamalProofDVR>";

}
