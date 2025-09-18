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

	String ElGamalCiphertextOPENING_TAG = "ElGamalCiphertext";
	String ElGamalDecryptionShareOPENING_TAG = "elGamalDecryptionShare";
	String PETCommitmentOPENING_TAG = "petC";
	String PETDecommitmentOPENING_TAG = "petD";
	String ElGamalPublicKeyOPENING_TAG = "elGamalPublicKey";
	String PublicKeyCiphertextOPENING_TAG = "publicKeyCiphertext";
	String PrivateKeyOPENING_TAG = "privateKey";
	String PublicKeyOPENING_TAG = "publicKey";
	String SharedKeyOPENING_TAG = "sharedKey";
	String SharedKeyCiphertextOPENING_TAG = "sharedKeyCiphertext";
	String SignatureOPENING_TAG = "signature";
	String VoteCapabilityOPENING_TAG = "voteCapability";
	String VoteCapabilityShareOPENING_TAG = "voteCapabilityShare";

	String CHARSET_NAME = "UTF-8";
}
