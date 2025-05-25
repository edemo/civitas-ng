package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface ProofKnowDiscLogTestData extends ElGamalPublicKeyCTestData {
	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 = "cZP5shp3SOALmUwEY6Zj6crdGhQzIB7fkbIS9sLCUzI4tZDlzROdfCm6EHz1sd5ekqrVwzRfwKBBuOrGeoujtfXJ7e1U5nxgCzcXTJAwhIsuM0KVyvgPV8krRzzRf1/hP7hSwzh4eWCpWKmzfREuoJBe0RKnrv+jBi0U46tSRRgz9qg5x3K92fTEtiCWQnLb5JlA+VWbJNxsdGm99o2t4wcmRdK2ZmOYFPdxmpCLLV5eCWCHCO/rZxBtWwhKScvwhjjbcnSlefNb4MXjEwk3j97Uulz7rt35KKDUMmcxDjpwHbkoAl7lbhtXNPgaPIrcIM60DDdclM8cwXz1ItvUbOzlprPLgjyskGj4g0Kmz+K6v/NfFRCKM2FA9QKlMSlpGWGsQt68+EYAvgTNAdoiSNdoDP+YBY1B6ITg384VcHOIzBhORrLupPR9iPUoIvU0GelBNciOhXjnFUjuWTSpsoCj47N9GBq9GGsYXpTBuXTMI/9wIsqUPT+sjOrvQERl";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_A = Util
			.asBigint(ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64);

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 = "V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_C = Util
			.asBigint(ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64);

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 = "ElIxK5N4qOvnJH6VBw+5us9mO1M/Y0t8WhDxjNKnHXk=";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_R = Util
			.asBigint(ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64);

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_XML = "<elGamalProofKnowDiscLog><a>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 + "</a><c>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 + "</c><r>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 + "</r><v>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</v></elGamalProofKnowDiscLog>";
	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML = "<elGamalProofKnowDiscLog><a>"
			+ "</a><c>" + "</c><r>" + "</r><v>" + "</v></elGamalProofKnowDiscLog>";

	public static final ElGamalProofKnowDiscLogC ELGAMAL_PROOF_KNOWN_DISC_LOG = new ElGamalProofKnowDiscLogC(
			ELGAMAL_PROOF_KNOWN_DISC_LOG_A, ELGAMAL_PROOF_KNOWN_DISC_LOG_C,
			ELGAMAL_PROOF_KNOWN_DISC_LOG_R, PUBLICIZED_BIGINT_A);

}
