/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface Constants {
	int CERTAINTY = 100;

	CivitasBigInteger ZERO = CivitasBigIntegerFactory.obtain(0);
	CivitasBigInteger ONE = CivitasBigIntegerFactory.obtain(1);
	CivitasBigInteger TWO = CivitasBigIntegerFactory.obtain(2);

	Random RANDOM = new SecureRandom();

	String MESSAGE_DIGEST_ALG = "SHA-256";
	String MESSAGE_DIGEST_PROVIDER = null;

	String SHARED_KEY_ALG = "AES";
	String SHARED_KEY_CIPHER_ALG = "AES";
	String SHARED_KEY_PROVIDER = "BC";
	int SHARED_KEY_LENGTH = 256;

	String PUBLIC_KEY_ALG = "RSA";

	String PUBLIC_KEY_CIPHER_ALG = "RSA/ECB/PKCS1Padding";
	String PUBLIC_KEY_SIGNATURE_ALG = "SHA512WithRSAEncryption";
	String PUBLIC_KEY_PROVIDER = "BC";
	int PUBLIC_KEY_LENGTH = 2048;

	int EL_GAMAL_GROUP_LENGTH = 3072;
	int EL_GAMAL_KEY_LENGTH = 256;

	int AUTHENTICATION_NONCE_LENGTH = 64;

	String EL_GAMAL_CIPHERTEXT_OPENING_TAG = "ElGamalCiphertext";
	String EL_GAMAL_DECRYPTION_SHARE_OPENING_TAG = "elGamalDecryptionShare";
	String PET_COMMITMENT_OPENING_TAG = "petC";
	String PET_DECOMMITMENT_OPENING_TAG = "petD";
	String EL_GAMAL_PUBLIC_KEY_OPENING_TAG = "elGamalPublicKey";
	String PUBLIC_KEY_CIPHERTEXT_OPENING_TAG = "publicKeyCiphertext";
	String PRIVATE_KEY_OPENING_TAG = "privateKey";
	String PUBLIC_KEY_OPENING_TAG = "publicKey";
	String SHARED_KEY_OPENING_TAG = "sharedKey";
	String SHARED_KEY_CIPHERTEXT_OPENING_TAG = "sharedKeyCiphertext";
	String SIGNATURE_OPENING_TAG = "signature";
	String VOTE_CAPABILITY_OPENING_TAG = "voteCapability";
	String VOTE_CAPABILITY_SHARE_OPENING_TAG = "voteCapabilityShare";

	Charset CHARSET = StandardCharsets.UTF_8;
}
