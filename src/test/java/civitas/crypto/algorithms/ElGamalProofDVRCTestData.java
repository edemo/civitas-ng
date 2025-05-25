package civitas.crypto.algorithms;

import civitas.common.Util;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.crypto.concrete.ElGamalPublicKeyCTestData;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalProofDVRCTestData extends ElGamalCiphertextCTestData,
		ElGamalPublicKeyCTestData, ElGamalReencryptFactorCTestData {

	public static final String DVR_U_BASE64 = "MrPYEhsuQnQS3bfumE7dMkcT3mzW+91OzA67xhDjXBs=";
	public static final String DVR_C_BASE64 = "ASkSAd1PJ+Qyc5LHaF2zkO/JuPcI67TMGFRuvVqNkLY=";
	public static final CivitasBigInteger DVR_C = Util.asBigint(DVR_C_BASE64);
	public static final CivitasBigInteger DVR_U = Util.asBigint(DVR_U_BASE64);

	public static final String EL_GAMAL_PROOF_DVR_XML = "<elGamalProofDVR>"
			+ EL_GAMAL_CIPHERTEXT_E_XML
			+ EL_GAMAL_CIPHERTEXT_A_ENCRYPTED_WITH_FACTOR_A_XML + "<c>" + DVR_C_BASE64
			+ "</c><w>" + RANDOMS_1_BASE64 + "</w><r>" + RANDOMS_2_BASE64 + "</r><u>"
			+ DVR_U_BASE64 + "</u></elGamalProofDVR>";

	ElGamalProofDVRC EL_GAMAL_PROOF_DVR = new ElGamalProofDVRC(
			EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_CIPHERTEXT_EPRIME, DVR_C, RANDOMS_1,
			RANDOMS_2, DVR_U);

}
