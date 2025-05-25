package civitas.crypto.concrete;

import java.security.MessageDigest;

import civitas.common.TestUtil;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.GenerateElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ConcreteTestData extends GenerateElGamalParametersTestData,
		ElGamalParametersCTestData, BasicValuesTestData,
		ElGamalDecryptionShareTestData, ElGamalPublicKeyCTestData,
		ElGamalCiphertextCTestData, ElGamal1OfLReencryptionCTestData, Constants {

	public static final MessageDigest BASELINE_DIGEST = TestUtil
			.getBaselineDigest();

	public static final CivitasBigInteger D_EXP_TWOK_FROMP = BIGINT_D
			.modPow(BIGINT_P.subtract(ONE).divide(BIGINT_Q), BIGINT_P);

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

	public static final int SOME_POSITIVE_INTEGER = 0x1eadbeef;
	public static final int KEYSIZE = 1024;

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

	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE = SOME_INT_BIG;

}