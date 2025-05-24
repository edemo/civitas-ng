package civitas.crypto.concrete;

import java.security.MessageDigest;

import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.crypto.algorithms.GenerateElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ConcreteTestData extends GenerateElGamalParametersTestData,
		ElGamalParametersCTestData, BasicValuesTestData,
		ElGamalDecryptionShareTestData, ElGamalPublicKeyCTestData,
		ElGamalCiphertextCTestData, ElGamal1OfLReencryptionCTestData {

	public static final MessageDigest BASELINE_DIGEST = TestUtil
			.getBaselineDigest();

	public static final CivitasBigInteger D_EXP_TWOK_FROMP = BIGINT_D.modPow(
			BIGINT_P.subtract(CivitasBigInteger.ONE).divide(BIGINT_Q), BIGINT_P);

	public static final String EL_GAMAL_PARAMETERS_SAFE_XML = "<elGamalParameters><p>"
			+ SAFE_P_BASE64 + "</p><q>" + SAFE_Q_BASE64 + "</q><g>" + SAFE_G_BASE64
			+ "</g></elGamalParameters>";

	public static final String VOTE_CAPABILITY_SHARE_XML = "<voteCapabilityShare>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapabilityShare>";
	public static final int VOTE_CAPABILITY_SHARE_INTVALUE = -2133817012;
	public static final String VOTE_CAPABILITY_SHARE_NULL_XML = "<voteCapabilityShare></voteCapabilityShare>";
	public static final String VOTE_CAPABILITY_SHARE_JUST_BIGINT_XML = "<voteCapabilityShare>"
			+ SOMESTRING_BASE64 + "</voteCapabilityShare>";

	public static final String VOTE_CAPABILITY_XML = "<voteCapability>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapability>";
	public static final int VOTE_CAPABILITY_INTVALUE = VOTE_CAPABILITY_SHARE_INTVALUE;
	public static final String VOTE_CAPABILITY_NULL_XML = "<voteCapability>"
			+ "</voteCapability>";
	public static final String VOTE_CAPABILITY_JUST_BIGINT_XML = "<voteCapability>"
			+ SOMESTRING_BASE64 + "</voteCapability>";

	public static final String PLAINTEXT_OF_BIG_SECRET_BASE64 = "AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9YkfiPHtYWgSk8=";
	public CivitasBigInteger PLAINTEXT_WITH_LEGENDRE_MINUS_ONE = CivitasBigInteger
			.valueOf(251121);

	public static final String ENCRYPTED_ZERO_FACTOR_XML = "<elGamalCiphertext><a>"
			+ ONE_BASE64 + "</a><b>" + PUBLICIZED_BIGINT_B_BASE64
			+ "</b></elGamalCiphertext>";

	public static final String SHARED_KEY_BASE64 = "dGVzdGRhdGE=";
	public static final String SHARED_KEY_NAME = "shared key name";
	public static final String SHARED_KEY_XML = "<sharedKey><n>" + SHARED_KEY_NAME
			+ "</n><k>" + SHARED_KEY_BASE64 + "</k></sharedKey>";
	public static final String SHARED_KEY_ON_WIRE = SHARED_KEY_NAME + "\n"
			+ SHARED_KEY_BASE64 + "\n";

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

	public static final String EL_GAMAL_KEY_SHARE_XML = "<elGamalKeyShare><pubKey>"
			+ EL_GAMAL_PUBLIC_KEY_XML + "</pubKey><proof>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_XML + "</proof></elGamalKeyShare>";
	public static final String EL_GAMAL_KEY_SHARE_NULL_XML = "<elGamalKeyShare><pubKey></pubKey><proof></proof></elGamalKeyShare>";

	public static final int SOME_POSITIVE_INTEGER = 0x1eadbeef;
	public static final int KEYSIZE = 1024;

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			CivitasBigInteger.ZERO);

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_C = new ElGamalReencryptFactorC(
			BIGINT_C);

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_D = new ElGamalReencryptFactorC(
			BIGINT_D);

	public static final String PROOF_VOTE_C_BASE64 = "JLjaqZUzF9Ja/0klrpAtIPe2sETCRf+0OlWeiI1TV/M=";
	public static final String PROOF_VOTE_S1_BASE64 = "S/9gPijKaeaDapS+YtHnBzKupvsiFDWRIaO5r1t1Ja8=";
	public static final String PROOF_VOTE_S2_BASE64 = "RpmdQslm/BT1fr9Bf2NsCdPMiY+ch4reYv+bEaa/RMo=";
	public static final String PROOF_VOTE_XML = "<elGamalProofVote><c>"
			+ PROOF_VOTE_C_BASE64 + "</c><s1>" + PROOF_VOTE_S1_BASE64 + "</s1><s2>"
			+ PROOF_VOTE_S2_BASE64 + "</s2></elGamalProofVote>";
	public static final String PROOF_VOTE_NULL_XML = "<elGamalProofVote><c></c><s1></s1><s2></s2></elGamalProofVote>";

	public static final CivitasBigInteger PROOF_VOTE_C = Util
			.asBigint(PROOF_VOTE_C_BASE64);
	public static final CivitasBigInteger PROOF_VOTE_S1 = Util
			.asBigint(PROOF_VOTE_S1_BASE64);
	public static final CivitasBigInteger PROOF_VOTE_S2 = Util
			.asBigint(PROOF_VOTE_S2_BASE64);

	public static final String DVR_U_BASE64 = "MrPYEhsuQnQS3bfumE7dMkcT3mzW+91OzA67xhDjXBs=";
	public static final String DVR_C_BASE64 = "ASkSAd1PJ+Qyc5LHaF2zkO/JuPcI67TMGFRuvVqNkLY=";
	public static final String EL_GAMAL_CIPHERTEXT_EPRIME_XML = "<elGamalCiphertext><a>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</a><b>PDSCaCKyqTWpyPY5S6xnPojOfrY1tegFiOyLfvjFuoRJr78VWRBm8vfoSeze7YrYXfjvozsV6c17vGebFg2lb0luMzg8a0CHKIra9KOOzAhHGXwTf86FI4MZdGg4WHrEn2HV/b3e4SUN+mDVj/rbEno+OYW1MGKPKy1kWhLJEN1pCEfYrBSx5nNEEhXUj8ENHzMFjcfC6aQryHrzYXOeBmAngCFXS7t+z2i9gvtQe/9uzFtvRstajNdbVawgUZUFvkyLVVC2uW9TZauKbBY53Y1q3xv/rcTrdEcJg3ll3i4wJOFDZSIt2BUM5nyMy4Fr5tTLecEhcycO8Tn11r/PZEqvunSV0hJT/M28n6mxIprBosjkENe+rLA0bSJVQ7Wrx9+Lcdotw5YZgrzwIKV53IuTjuC1+Ttq+PQ4c5ga7DHj14L4fl4kIntPXTjvoGOAe/UW22tdlGL7USadlbLhF0pQT9FoMh9vbtAtJo/BQqFLgEdzTWXQevUWkGiPYcB+</b></elGamalCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_E_XML = "<elGamalCiphertext><a>R/gEXJbvQz2fT1ugfIDbEqeMcW546Bb38EK6rgRF/lHWXEIFDuniOe93WieT06kVdhbaPZlimFIt8wj7I9M6Bwg9zTjUdZ69n1FSPncj1zdztC74q6cYekz4ZtHIgz15q7diOMP8gIGZK+f3wQ9ArmZ8WBibEo5jRESUX5MdroyYia5+Yyk8SMeaVve9nEE+koZf3MdOCB4vv9kxSJ2tHIXMwpsh5qg+wTKMiq8YJFeGfoJePWPZKivIwpwnWKsfiHzNmp6GgZ537nRRZ16RAHoEIGhiRriFeH45EHYiyRkdbzQqUx1FqX2aCuyCeOE5HfefDQSzT/z0I/LGgzB1elxcj/oJu6DdfheorS1rqDELCfLoH4bzl0IZsceLBecIdnj+rfqVm5AYoB7ZMLOxRtrMihiMj/melAg658z2ATzbw5hJxJADxKn968bhVQL7HaAMmaYMMhb8kxrScwYRq0JA5tJB8g/DsuGlSx9zCFfZYYugHMVMkdnq45oUEZPR</a><b>bMsqXdO9UQpcmYMlXfsBRZ2m6HLMwWegMiHCleYllF7UfbY89g90TS0YXy2qszrFzDA/01C2/vuenadInhGxf45pF/fZ7ujcHORd3iYqIJbDokjZwXGVWCgpCa6maOLXJG+kGvWrWd3jEgGwSmTwRsboqpMgVJ2F8GdsZZfaePL8/SQdALJOFb9Ga8ffSs8dg0w7tshe83jnYSe6IDuH9tUweHhd1/HaSLKBhdIduumylq/jCOXzbbim5L3anJb2Dn5vcR9ecf4W8/0/vSSkED/GJF1igZ8S7sqaqlxvmT72lwN5+u4uFOevjD0/yxLYjpeXWMhOIOSk7mtEps4HGrOoDBI6qg9eDeIkLoxbYhg+9IXAsbR9ubTe067gUUujT4AZouZl5Vj6ZbElD1nCSOKytZVUm40m9cwzmSDypXC/lxiR0SOYTlx0jYw6eRmRE/NxztlCWTF52jkVkliCU5vvY6Yw0qb7g7CQzcOPKf2Dby1an+wO+SmqV58t5YY+</b></elGamalCiphertext>";
	public static final String EL_GAMAL_PROOF_DVR_XML = "<elGamalProofDVR>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_CIPHERTEXT_EPRIME_XML + "<c>"
			+ DVR_C_BASE64 + "</c><w>" + RANDOMS_1_BASE64 + "</w><r>"
			+ RANDOMS_2_BASE64 + "</r><u>" + DVR_U_BASE64 + "</u></elGamalProofDVR>";
	public static final CivitasBigInteger DVR_C = Util.asBigint(DVR_C_BASE64);
	public static final CivitasBigInteger DVR_U = Util.asBigint(DVR_U_BASE64);

	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE = TestUtil
			.construct(VoteCapabilityShareC.class, BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE_JUST_BIGINT = TestUtil
			.construct(VoteCapabilityShareC.class, BIGINT_A);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_SAFE = new ElGamalParametersC(
			SAFE_P, SAFE_Q, SAFE_G);
	public static final VoteCapabilityC VOTE_CAPABILITY = TestUtil
			.construct(VoteCapabilityC.class, BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final VoteCapabilityC VOTE_CAPABILITY_JUST_BIGINT = TestUtil
			.construct(VoteCapabilityC.class, BIGINT_A);

	public static final ElGamalProofKnowDiscLogC ELGAMAL_PROOF_KNOWN_DISC_LOG = new ElGamalProofKnowDiscLogC(
			ELGAMAL_PROOF_KNOWN_DISC_LOG_A, ELGAMAL_PROOF_KNOWN_DISC_LOG_C,
			ELGAMAL_PROOF_KNOWN_DISC_LOG_R, PUBLICIZED_BIGINT_A);
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_C = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY, ELGAMAL_PROOF_KNOWN_DISC_LOG);

	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_E = TestUtil
			.ElGamalCiphertextCfromXML(EL_GAMAL_CIPHERTEXT_E_XML);
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_EPRIME = TestUtil
			.ElGamalCiphertextCfromXML(EL_GAMAL_CIPHERTEXT_EPRIME_XML);
	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE = SOME_INT_BIG;

}