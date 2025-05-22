package civitas.crypto.algorithms;

import java.util.Base64;

import civitas.crypto.SchnorrPrime;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalParametersCTestData;
import civitas.util.CivitasBigInteger;

public interface GenerateElGamalParametersTestData
		extends ElGamalParametersCTestData {
	public static final int SAFE_KEY_LENGTH = CryptoFactoryC.EL_GAMAL_GROUP_LENGTH
			- 1;
	public static final String SAFE_P_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9QDfV8=";
	public static final String SAFE_Q_BASE64 = "AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9Yk8ok7KeoBvq8=";
	public static final String SAFE_G_BASE64 = "AKhY5FkyO989WVWT54UIYi29aZjUfx1XD0xlcF13Y7nGa7itniOZIj36m+m8XHW9VN0bzCgLh4btlHBQ8AY3dMo=";
	public static final CivitasBigInteger SAFE_P = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_P_BASE64));
	public static final CivitasBigInteger SAFE_Q = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_Q_BASE64));
	public static final CivitasBigInteger SAFE_G = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_G_BASE64));

	public static final SchnorrPrime SAFE_PRIMES = new SchnorrPrime(SAFE_P,
			SAFE_Q);
	public static final SchnorrPrime SCHNORR_PRIMES = new SchnorrPrime(BIGINT_P,
			BIGINT_Q);

}
