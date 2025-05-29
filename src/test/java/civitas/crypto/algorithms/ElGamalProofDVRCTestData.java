package civitas.crypto.algorithms;

import civitas.common.Util;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.util.CivitasBigInteger;

public interface ElGamalProofDVRCTestData extends ElGamalCiphertextCTestData {

	String DVR_HASH_BASE64 = "AJjM7UWkhFgdnqy5yiDJJMUKuLDFD/k8Yqv74UN9Qhp6";
	CivitasBigInteger DVR_HASH = Util.asBigint(DVR_HASH_BASE64);

	CivitasBigInteger DVR_U = RANDOMS_0.modAdd(
			ZETA.modMultiply(DVR_HASH.modAdd(RANDOMS_1, BIGINT_Q), BIGINT_Q),
			BIGINT_Q);
	String DVR_U_BASE64 = Util.fromBigInt(DVR_U);
	ElGamalProofDVRC EL_GAMAL_PROOF_DVR = new ElGamalProofDVRC(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, DVR_HASH, RANDOMS_1, RANDOMS_2, DVR_U);

	public static final String EL_GAMAL_PROOF_DVR_XML = "<elGamalProofDVR>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_CIPHERTEXT_EPRIME_XML + "<c>"
			+ DVR_HASH_BASE64 + "</c><w>" + RANDOMS_1_BASE64 + "</w><r>"
			+ RANDOMS_2_BASE64 + "</r><u>" + DVR_U_BASE64 + "</u></elGamalProofDVR>";

}
