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

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.keypair.ElGamalKeyPair;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.petshare.PETShare;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
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

public interface CryptoFactory {
	/**
	 * Returns an array of length size that is a permutation, i.e. i maps to j iff
	 * ret[i] == j
	 */
	int[] createPermutation(int size);

//    KeyPairPrincipal keyPair(String name, ElGamalPublicKey publicKey, ElGamalPrivateKey privateKey);
//    KeyPairPrincipal keyPair(String name, PublicKey publicKey);
//    KeyPairPrincipal keyPair(String name, PublicKey publicKey, PrivateKey privateKey);

	// TODO: it is unclear what the label on the return result should be.
	ElGamalPublicKey egPubKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	ElGamalPrivateKey egPrivKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	PublicKey publicKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	PrivateKey privateKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException;

	KeyPair generateKeyPair(int keyLength);

	ElGamalParameters generateElGamalParameters();

	ElGamalParameters generateElGamalParameters(int keyLength, int groupLength);

	ElGamalKeyPair generateElGamalKeyPair(ElGamalParameters params);

	ElGamalKeyPairShare generateKeyPairShare(ElGamalParameters params);

	ElGamalKeyShare constructKeyShare(ElGamalKeyPairShare kps);

	ElGamalPublicKey combineKeyShares(ElGamalKeyShare[] shares)
			throws CryptoException;

	ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key, ElGamalMsg msg);

	ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor r);

	ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key, ElGamalCiphertext c);

	ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key, ElGamalCiphertext c,
			ElGamalReencryptFactor r);

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

	ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key, ElGamalCiphertext ciphertext)
			throws CryptoException;

	ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoException;

	ElGamalProofKnowDiscLog constructProofKnowDiscLog(ElGamalParameters params,
			ElGamalPrivateKey k);

	ElGamalProofDVR constructElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalCiphertext e,
			ElGamalCiphertext ePrime, ElGamalReencryptFactor er,
			ElGamalReencryptFactor erPrime);

	ElGamalProofDVR constructFakeElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertext e, ElGamalCiphertext ePrime);

	PETShare constructPETShare(ElGamalParameters params, ElGamalCiphertext a,
			ElGamalCiphertext b);

	ElGamalCiphertext combinePETShareDecommitments(PETDecommitment[] decs,
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

	ElGamalDecryptionShare constructDecryptionShare(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare);

	ElGamalMsg combineDecryptionShares(ElGamalCiphertext c,
			ElGamalDecryptionShare[] shares, ElGamalParameters params)
			throws CryptoException;

	ElGamal1OfLReencryption elGamal1OfLReencrypt(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice,
			ElGamalReencryptFactor reencryptFactor);

	int elGamal1OfLValue(ElGamalMsg m, int L, ElGamalParameters params)
			throws CryptoException;

	ProofVote constructProofVote(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamal1OfLReencryption encChoice,
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
	Signature signature(PrivateKey k, PublicKeyMsg m);

	boolean publicKeyVerifySignature(PublicKey K, Signature s, PublicKeyMsg m);

	PublicKeyMsg publicKeyVerifySignatureMsg(PublicKey K, Signature s,
			PublicKeyMsg m);

	Signature signature(PrivateKey k, byte[] bytes);

	boolean publicKeyVerifySignature(PublicKey K, Signature s, byte[] bytes);

	/*
	 * XML parsing methods
	 */
	ElGamalPublicKey elGamalPublicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalPrivateKey elGamalPrivateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalParameters elGamalParametersFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalProofKnowDiscLog elGamalProofKnowDiscLogFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalProofDiscLogEquality elGamalProofDiscLogEqualityFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalCiphertext elGamalCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalSignedCiphertext elGamalSignedCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalDecryptionShare decryptionShareFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PETCommitment petCommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PETDecommitment petDecommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalKeyShare elGamalKeyShareFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamal1OfLReencryption elGamal1OfLReencryptionFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalProof1OfL elGamalProof1OfLFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalReencryptFactor elGamalReencryptFactorFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PrivateKey privateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PublicKeyCiphertext publicKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	SharedKeyCiphertext sharedKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	SharedKey sharedKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	SharedKey sharedKeyFromWire(Reader r)
			throws IllegalArgumentException, IOException;

	ElGamalProofDVR elGamalProofDVRFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	VoteCapability voteCapabilityFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	VoteCapabilityShare voteCapabilityShareFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PublicKey publicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	Signature signatureFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	ProofVote proofVoteFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	PETShare petShareFromXML(Reader r)
			throws IllegalArgumentException, IOException;

	/*
	 * Factory methods
	 */
	ElGamalMsg elGamalMsg(int m, ElGamalParameters p) throws CryptoException;

	ElGamalMsg elGamalMsg(String m, ElGamalParameters p) throws CryptoException;

	ElGamalKeyShare elGamalKeyShare(ElGamalPublicKey K,
			ElGamalProofKnowDiscLog proof);

	PublicKeyMsg publicKeyMsg(String m) throws CryptoException;

	SharedKeyMsg sharedKeyMsg(String m) throws CryptoException;

}
