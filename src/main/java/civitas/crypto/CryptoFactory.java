/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import civitas.common.VoteChoice;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.keypair.ElGamalKeyPair;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.msg.CryptMessage;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.petshare.PETShare;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.rsakeypair.KeyPair;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsapublickey.PublicKey;
import civitas.crypto.sharedkey.SharedKey;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.crypto.signature.Signature;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.votecapability.VoteCapability;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import civitas.util.CivitasBigInteger;

public interface CryptoFactory {

	int[] createPermutation(int size);

	ElGamalPublicKey egPubKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	ElGamalPrivateKey egPrivKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	PublicKey publicKeyFromFile(String keyFile) throws IllegalArgumentException,
			FileNotFoundException, IOException, InvalidKeySpecException;

	PrivateKey privateKeyFromFile(String keyFile) throws IllegalArgumentException,
			FileNotFoundException, IOException, InvalidKeySpecException;

	KeyPair generateKeyPair(int keyLength);

	ElGamalParameters generateElGamalParameters();

	ElGamalParameters generateElGamalParameters(int keyLength, int groupLength);

	ElGamalKeyPair generateElGamalKeyPair(ElGamalParameters params);

	ElGamalKeyPairShare generateKeyPairShare(ElGamalParameters params);

	ElGamalKeyShare constructKeyShare(ElGamalKeyPairShare kps);

	ElGamalPublicKey combineKeyShares(ElGamalKeyShare[] shares)
			throws CryptoException;

	ElGamalCiphertextish elGamalEncrypt(ElGamalPublicKey key, ElGamalMsg msg);

	ElGamalCiphertextish elGamalEncrypt(ElGamalPublicKey key, CryptMessage msg,
			ElGamalReencryptFactor r);

	ElGamalCiphertextish elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertextish c);

	ElGamalCiphertextish elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertextish c, ElGamalReencryptFactor r);

	ElGamalReencryptFactor generateElGamalReencryptFactor(
			ElGamalParameters params);

	ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg);

	ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r);

	ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r, byte[] additionalEnv);

	boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext);

	boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv);

	ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalCiphertextish ciphertext) throws CryptoException;

	ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoException;

	ElGamalProofKnowDiscLog constructProofKnowDiscLog(ElGamalParameters params,
			ElGamalPrivateKey k);

	ElGamalProofDVR constructElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalCiphertextish e,
			ElGamalCiphertext ePrime, ElGamalReencryptFactor er,
			ElGamalReencryptFactor erPrime);

	ElGamalProofDVR constructFakeElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertextish e, ElGamalCiphertext ePrime);

	PETShare constructPETShare(ElGamalParameters params, ElGamalCiphertextish a,
			ElGamalCiphertextish b);

	ElGamalCiphertextish combinePETShareDecommitments(PETDecommitment[] decs,
			ElGamalParameters params) throws CryptoException;

	/**
	 * returns true iff the ElGamalMessage resulting from the PET says the plain
	 * texts are equivalent
	 */
	boolean petResult(ElGamalMsg petResult);

	VoteCapabilityShare generateVoteCapabilityShare(ElGamalParameters params);

	VoteCapability[] combineVoteCapabilityShares(VoteCapabilityShare[][] shares,
			ElGamalParameters params);

	CiphertextList multiplyCiphertexts(ElGamalSignedCiphertext[][] shares,
			ElGamalParameters params);

	CiphertextList constructWellKnownCiphertexts(ElGamalPublicKey key, int count);

	ElGamalDecryptionShare constructDecryptionShare(ElGamalCiphertextish c,
			ElGamalKeyPairShare keyShare);

	ElGamalMsg combineDecryptionShares(ElGamalCiphertextish c,
			ElGamalDecryptionShare[] shares, ElGamalParameters params)
			throws CryptoException;

	ElGamal1OfLReencryption elGamal1OfLReencrypt(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice,
			ElGamalReencryptFactor reencryptFactor);

	VoteChoice elGamal1OfLValue(ElGamalMsg m,
			Map<CivitasBigInteger, VoteChoice> map) throws CryptoException;

	ProofVote constructProofVote(ElGamalParameters params,
			ElGamalCiphertextish encCapability, ElGamal1OfLReencryption encChoice,
			String context, ElGamalReencryptFactor encCapabilityFactor,
			ElGamalReencryptFactor encChoiceFactor);

	/*
	 * Public Key and shared key encryption
	 */
	PublicKeyCiphertext publicKeyEncrypt(PublicKey key, PublicKeyMsg msg);

	PublicKeyMsg publicKeyDecrypt(PrivateKey key, PublicKeyCiphertext ciphertext)
			throws CryptoException, UnsupportedEncodingException, CryptoError;

	SharedKeyCiphertext sharedKeyEncrypt(SharedKey key, SharedKeyMsg msg);

	SharedKeyMsg sharedKeyDecrypt(SharedKey key, SharedKeyCiphertext ciphertext)
			throws CryptoException, UnsupportedEncodingException, CryptoError;

	SharedKey generateSharedKey(int keyLength);

	/*
	 * hashing and nonces Note that we assume the digest does not reveal any
	 * information about the data used to produce the digest.
	 */
	byte[] freshNonce(int bitlength);

	MessageDigest messageDigest();

	byte[] messageDigest(byte[] a, int b);

	byte[] messageDigest(byte[] a);

	byte[] messageDigest(byte[] a, int b, boolean constBytes);

	byte[] messageDigest(byte[] a, boolean constBytes);

	byte[] messageDigest(String s);

	/*
	 * Base64 methods
	 */
	String freshNonceBase64(int bitlength);

	String bytesToBase64(byte[] a);

	String constBytesToBase64(byte[] a);

	/*
	 * Randomness
	 */

	/**
	 * Return a non-negative int less than n. n must be a positive integer.
	 */
	int randomInt(int n);

	/*
	 * Public key signing operations
	 */
	Signature signature(PrivateKey k, PublicKeyMsg m)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError;

	boolean publicKeyVerifySignature(PublicKey K, Signature s, PublicKeyMsg m);

	PublicKeyMsg publicKeyVerifySignatureMsg(PublicKey K, Signature s,
			PublicKeyMsg m);

	Signature signature(PrivateKey k, byte[] bytes)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError;

	boolean publicKeyVerifySignature(PublicKey K, Signature s, byte[] bytes);

	ElGamalMsg elGamalMsg(int m, ElGamalParameters p) throws CryptoException;

	ElGamalMsg elGamalMsg(String m, ElGamalParameters p) throws CryptoException;

	ElGamalKeyShare elGamalKeyShare(ElGamalPublicKey K,
			ElGamalProofKnowDiscLog proof);

	PublicKeyMsg publicKeyMsg(String m) throws CryptoException;

	SharedKeyMsg sharedKeyMsg(String m) throws CryptoException;

	SharedKey sharedKeyFromWire(Reader r)
			throws IllegalArgumentException, IOException;

}
