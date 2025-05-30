package civitas.crypto.concrete;

import java.security.MessageDigest;

import civitas.common.TestUtil;
import civitas.crypto.algorithms.Constants;
import civitas.util.CivitasBigInteger;

public interface ConcreteTestData
		extends ElGamalParametersCTestData, BasicValuesTestData,
		ElGamalDecryptionShareTestData, ElGamalPublicKeyCTestData,
		ElGamalCiphertextCTestData, ElGamal1OfLReencryptionCTestData, Constants {

	public static final MessageDigest BASELINE_DIGEST = TestUtil
			.getBaselineDigest();

	public static final CivitasBigInteger D_EXP_TWOK_FROMP = BIGINT_D
			.modPow(BIGINT_P.subtract(ONE).divide(BIGINT_Q), BIGINT_P);

	public static final String PLAINTEXT_OF_BIG_SECRET_BASE64 = "AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9YkfiPHtYWgSk8=";

	public static final String SHARED_KEY_BASE64 = "dGVzdGRhdGE=";
	public static final String SHARED_KEY_NAME = "shared key name";
	public static final String SHARED_KEY_XML = "<sharedKey><n>" + SHARED_KEY_NAME
			+ "</n><k>" + SHARED_KEY_BASE64 + "</k></sharedKey>";
	public static final String SHARED_KEY_ON_WIRE = SHARED_KEY_NAME + "\n"
			+ SHARED_KEY_BASE64 + "\n";

	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE = SOME_INT_BIG;

}