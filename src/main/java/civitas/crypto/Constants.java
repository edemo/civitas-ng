/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.security.SecureRandom;
import java.util.Random;

import civitas.util.CivitasBigInteger;

public interface Constants {
	/**
	 * 2^-CERTAINTY is false positive rate for probablePrime.
	 */
	public final static int CERTAINTY = 80; // 2^80 recommended by FIPS 186.

	public static final CivitasBigInteger ZERO = CivitasBigInteger.valueOf(0);
	public static final CivitasBigInteger ONE = CivitasBigInteger.valueOf(1);
	public static final CivitasBigInteger TWO = CivitasBigInteger.valueOf(2);

//TODO: should we be requesting a specific RNG algorithm in the constructor
//call?
	public static final Random RANDOM = new SecureRandom();

	/*
	 * The following constants define the algorithms and providers to use.
	 */
	public final String MESSAGE_DIGEST_ALG = "SHA-256";
	public final String MESSAGE_DIGEST_PROVIDER = null; // use any provider

	public final String SHARED_KEY_ALG = "AES";
	public final String SHARED_KEY_CIPHER_ALG = "AES"; // "AES/CBC/PKCS7Padding";
	public final String SHARED_KEY_PROVIDER = "BC";

	public final String PUBLIC_KEY_ALG = "RSA";

	public final String PUBLIC_KEY_CIPHER_ALG = "RSA/ECB/PKCS1Padding";
//  public final String PUBLIC_KEY_CIPHER_ALG = "RSA/NONE/OAEPPADDING";
	public final String PUBLIC_KEY_SIGNATURE_ALG = "SHA512WithRSAEncryption";
	public final String PUBLIC_KEY_PROVIDER = "BC";

	public static final int EL_GAMAL_GROUP_LENGTH = 3072; // size in bits for p
	public static final int EL_GAMAL_KEY_LENGTH = 256; // size in bits for q

}
