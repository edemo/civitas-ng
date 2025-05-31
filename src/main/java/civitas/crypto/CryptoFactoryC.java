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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.ConvertToBase64;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.CreatePermutation;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.algorithms.GenerateRandomInt;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.algorithms.GetRandomGenerator;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.ciphertext.MultiplyCiphertexts;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.decriptionshare.CombineDecryptionShares;
import civitas.crypto.decriptionshare.ConstructElGamalDecryptionShare;
import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.decriptionshare.ElGamalDecryptionShareFromXML;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.keypair.ElGamalKeyPair;
import civitas.crypto.keypair.GenerateElGamalKeyPair;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.keypairshare.GenerateKeyPairShare;
import civitas.crypto.keyshare.CombineKeyShares;
import civitas.crypto.keyshare.ConstructElGamalKeyShare;
import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.keyshare.ElGamalKeyShareFromXML;
import civitas.crypto.messagedigest.ComputeMessageDigest;
import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.messagedigest.ObtainMessageDigest;
import civitas.crypto.msg.DecryptElGamalMessage;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.oneoflreencryption.ConstructElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionFromXML;
import civitas.crypto.parameters.BruteForceDecode;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersFromXML;
import civitas.crypto.parameters.GenerateElGamalParameters;
import civitas.crypto.petcommitment.CombinePETShareDecommitments;
import civitas.crypto.petcommitment.IsPetResult;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.petdecommitment.PetCommitmentFromXML;
import civitas.crypto.petdecommitment.PetDecommitmentFromXML;
import civitas.crypto.petshare.ConstructPETShare;
import civitas.crypto.petshare.PETShare;
import civitas.crypto.petshare.PetShareFromXML;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.privatekey.ElGamalPrivateKeyFromFile;
import civitas.crypto.privatekey.ElGamalPrivateKeyFromXML;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ConstructWellKnownCiphertexts;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.crypto.proof1ofl.ElGamalProof1OfLFromXML;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityFromXML;
import civitas.crypto.proofdvr.ConstructElGamalProofDVR;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.proofdvr.ElGamalProofDVRFromXML;
import civitas.crypto.proofdvr.FakeElGamalProofDVRC;
import civitas.crypto.proofknowndisclog.ConstructProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogFromXML;
import civitas.crypto.proofvote.ConstructProofVote;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.proofvote.ProofVoteFromXML;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyFromFile;
import civitas.crypto.publickey.ElGamalPublicKeyFromXML;
import civitas.crypto.publickeyciphertext.EncryptPublic;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextFromXML;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.publickeymsg.VerifyPublicKeySignatureMsg;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorFromXML;
import civitas.crypto.rsakeypair.GenerateKeyPair;
import civitas.crypto.rsakeypair.KeyPair;
import civitas.crypto.rsaprivatekey.CreatePrivateKeyFromBytes;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsaprivatekey.PrivateKeyFromXML;
import civitas.crypto.rsaprivatekey.PrivatekeyFromFile;
import civitas.crypto.rsapublickey.CreatePublicKeyFromBytes;
import civitas.crypto.rsapublickey.DecryptPublic;
import civitas.crypto.rsapublickey.PublicKey;
import civitas.crypto.rsapublickey.PublicKeyFromFile;
import civitas.crypto.rsapublickey.PublicKeyFromXML;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.sharedkey.CreateSharedKeyFromBytes;
import civitas.crypto.sharedkey.DecryptShared;
import civitas.crypto.sharedkey.GenerateSharedKey;
import civitas.crypto.sharedkey.GetSharedKeyGenerator;
import civitas.crypto.sharedkey.SharedKey;
import civitas.crypto.sharedkey.SharedKeyFromWire;
import civitas.crypto.sharedkey.SharedKeyFromXML;
import civitas.crypto.sharedkeyciphertext.EncryptShared;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;
import civitas.crypto.signature.SignatureFromXML;
import civitas.crypto.signature.VerifyElGamalSignature;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextFromXML;
import civitas.crypto.signedciphertext.SharedKeyCiphertextFromXML;
import civitas.crypto.signedciphertext.SignAndEncrypt;
import civitas.crypto.votecapability.VoteCapability;
import civitas.crypto.votecapability.VoteCapabilityFromXML;
import civitas.crypto.votecapabilityshare.CombineVoteCapabilityShares;
import civitas.crypto.votecapabilityshare.GenerateVoteCapabilityShare;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;
import civitas.util.Use;

public class CryptoFactoryC implements CryptoFactory, Constants {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;
	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	GenerateElGamalParameters generateElGamalParameters;
	@Use
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Use
	FakeElGamalProofDVRC fakeElGamalProofDVRC;
	@Use
	ConstructElGamalDecryptionShare constructElGamalDecryptionShare;
	@Use
	private static GetRandomGenerator getRandomGenerator;
	@Use
	ObtainMessageDigest obtainMessageDigest;
	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalEncrypt elGamalEncrypt;
	@Use
	ConstructProofVote constructProofVote;
	@Use
	SignAndEncrypt signAndEncrypt;
	@Use
	GenerateElGamalKeyPair generateElGamalKeyPair;
	@Use
	ConstructProofKnowDiscLog constructProofKnowDiscLog;
	@Use
	ElGamalReencrypt elGamalReencrypt;
	@Use
	GenerateKeyPairShare generateKeyPairShare;
	@Use
	GetSharedKeyGenerator getSharedKeyGenerator;
	@Use
	CreatePermutation createPermutation;
	@Use
	GenerateRandomInt generateRandomInt;
	@Use
	GetPublicKeyGenerator getPublicKeyGenerator;
	@Use
	GenerateKeyPair generateKeyPair;
	@Use
	CreateFreshNonce createFreshNonce;
	@Use
	CreateFreshNonceBase64 createFreshNonceBase64;
	@Use
	static ConvertToBase64 convertToBase64;
	@Use
	GenerateVoteCapabilityShare generateVoteCapabilityShare;
	@Use
	CombineVoteCapabilityShares combineVoteCapabilityShares;
	@Use
	MultiplyCiphertexts multiplyCiphertexts;
	@Use
	CombineKeyShares combineKeyShares;
	@Use
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;
	@Use
	ConstructWellKnownCiphertexts constructWellKnownCiphertexts;
	@Use
	ConvertHashToBigInt convertHashToBigInt;
	@Use
	VerifyElGamalSignature verifyElGamalSignature;
	@Use
	DecryptElGamalMessage decryptElGamalMessage;
	@Use
	ConstructPETShare constructPETShare;
	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;
	@Use
	ElGamalSignedCiphertextFromXML elGamalSignedCiphertextFromXML;
	@Use
	static ConvertToBigInt convertToBigInt;
	@Use
	ProofVoteFromXML proofVoteFromXML;
	@Use
	CombineDecryptionShares combineDecryptionShares;
	@Use
	PetShareFromXML petShareFromXML;
	@Use
	ElGamalProof1OfLFromXML elGamalProof1OfLFromXML;
	@Use
	ElGamal1OfLReencryptionFromXML elGamal1OfLReencryptionFromXML;
	@Use
	VoteCapabilityShareFromXML voteCapabilityShareFromXML;
	@Use
	VoteCapabilityFromXML voteCapabilityFromXML;
	@Use
	ElGamalProofDiscLogEqualityFromXML elGamalProofDiscLogEqualityFromXML;
	@Use
	PetDecommitmentFromXML petDecommitmentFromXML;
	@Use
	PetCommitmentFromXML petCommitmentFromXML;
	@Use
	SharedKeyFromWire sharedKeyFromWire;
	@Use
	SharedKeyFromXML sharedKeyFromXML;
	@Use
	SharedKeyCiphertextFromXML sharedKeyCiphertextFromXML;
	@Use
	PublicKeyCiphertextFromXML publicKeyCiphertextFromXML;
	@Use
	ElGamalReencryptFactorFromXML elGamalReencryptFactorFromXML;
	@Use
	ElGamalDecryptionShareFromXML decryptionShareFromXML;
	@Use
	IsPetResult isPetResult;
	@Use
	CombinePETShareDecommitments combinePETShareDecommitments;
	@Use
	ElGamalPrivateKeyFromXML ElGamalPrivateKeyFromXML;
	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;
	@Use
	ElGamalPrivateKeyFromFile elGamalPrivateKeyFromFile;
	@Use
	ElGamalProofDVRFromXML elGamalProofDVRFromXML;
	@Use
	PrivatekeyFromFile privatekeyFromFile;
	@Use
	PublicKeyFromFile publicKeyFromFile;
	@Use
	PublicKeyFromXML publicKeyFromXML;
	@Use
	ElGamalKeyShareFromXML elGamalKeyShareFromXML;
	@Use
	SignWithPublicKey signWithPublicKey;
	@Use
	ComputeMessageDigest computeMessageDigest;
	@Use
	DecryptPublic decryptPublic;
	@Use
	EncryptPublic encryptPublic;
	@Use
	DoCrypto doCrypto;
	@Use
	EncryptShared encryptShared;
	@Use
	DecryptShared decryptShared;
	@Use
	CreateSharedKeyFromBytes createSharedKeyFromBytes;
	@Use
	CreatePublicKeyFromBytes createPublicKeyFromBytes;
	@Use
	CreatePrivateKeyFromBytes createPrivateKeyFromBytes;
	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;
	@Use
	SignatureFromXML signatureFromXML;
	@Use
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Use
	VerifyPublicKeySignatureMsg verifyPublicKeySignatureMsg;
	@Use
	PrivateKeyFromXML privateKeyFromXML;
	@Use
	ConstructElGamalKeyShare constructElGamalKeyShare;
	@Use
	ConstructElGamalProofDVR constructElGamalProofDVR;
	@Use
	GenerateSharedKey generateSharedKey;
	@Use
	ElGamalPublicKeyFromFile elGamalPublicKeyFromFile;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	public static SecretKeyFactory sharedKeyFactory;
	public static KeyFactory publicKeyFactory;

	static {
		BouncyCastleProvider bc = new BouncyCastleProvider();
		Security.addProvider(bc);

//      // dump the keys and services of the providers.
//      Provider[] ps = Security.getProviders();
//      for (int i = 0; i < ps.length; i++) {
//      System.err.println("Provider: " + ps[i].getName());
//      System.err.println("Key set");
//      System.err.println("=======");
//      System.err.println(ps[i].keySet());
//      System.err.println("Services");
//      System.err.println("========");
//      System.err.println(ps[i].getServices());
//      }
	}

	private static CryptoFactoryC singleton;

	public static CryptoFactoryC singleton() {
		if (null == singleton) {
			singleton = DI.get(CryptoFactoryC.class);
		}

		return singleton;
	}

	private CryptoFactoryC() {
		initializeCryptoProviders();
	}

	/**
	 * Initialize the crypto providers.
	 *
	 */
	private void initializeCryptoProviders() {
		try {
			sharedKeyFactory = SecretKeyFactory.getInstance(SHARED_KEY_ALG,
					SHARED_KEY_PROVIDER);
			publicKeyFactory = KeyFactory.getInstance(PUBLIC_KEY_ALG,
					PUBLIC_KEY_PROVIDER);
		} catch (NoSuchAlgorithmException | NoSuchProviderException impossible) {
			throw new Error(impossible);
		}
	}

	@Override
	public int[] createPermutation(int size) {
		return createPermutation.apply(size);
	}

	@Override
	@Deprecated
	public ElGamalParameters generateElGamalParameters(int keyLength,
			int groupLength) {
		return generateElGamalParameters.apply(keyLength, groupLength);
	}

	@Deprecated
	public ElGamalParameters generateElGamalParameters(int keyLength) {
		return generateElGamalParameters.apply(keyLength, keyLength + 1);
	}

	@Override
	public ElGamalParameters generateElGamalParameters() {
		return generateElGamalParameters.apply();
	}

	@Override
	public ElGamalKeyPair generateElGamalKeyPair(ElGamalParameters p) {
		return generateElGamalKeyPair.apply(p);
	}

	@Override
	public KeyPair generateKeyPair(int keyLength) {
		return generateKeyPair.apply(keyLength);
	}

	@Override
	public ElGamalKeyPairShare generateKeyPairShare(ElGamalParameters params) {
		return generateKeyPairShare.apply(params);
	}

	@Override
	public VoteCapabilityShare generateVoteCapabilityShare(ElGamalParameters p) {
		return generateVoteCapabilityShare.apply(p);
	}

	@Override
	public VoteCapability[] combineVoteCapabilityShares(
			VoteCapabilityShare[][] shares, ElGamalParameters p) {
		return combineVoteCapabilityShares.apply(shares, p);
	}

	@Override
	public CiphertextList multiplyCiphertexts(
			ElGamalSignedCiphertext[][] ciphertexts, ElGamalParameters p) {
		return multiplyCiphertexts.apply(ciphertexts, p);
	}

	@Override
	public ElGamalPublicKey combineKeyShares(ElGamalKeyShare[] shares)
			throws CryptoException {
		return combineKeyShares.apply(shares);
	}

	@Override
	public ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg) {
		return elGamalEncrypt.apply(key, msg);
	}

	@Override
	public ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor encryptFactor) {
		return elGamalEncrypt.apply(key, msg, encryptFactor);
	}

	@Override
	public ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext) {
		return elGamalReencrypt.apply(key, ciphertext);
	}

	@Override
	public ElGamalReencryptFactor generateElGamalReencryptFactor(
			ElGamalParameters params) {
		return elGamalReencrypt.apply(params);

	}

	@Override
	public ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext, ElGamalReencryptFactor factor) {
		return elGamalReencrypt.apply(key, ciphertext, factor);
	}

	@Override
	public ElGamal1OfLReencryption elGamal1OfLReencrypt(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice,
			ElGamalReencryptFactor factor) {
		return constructElGamal1OfLReencryption.apply(key, ciphertexts, L, choice,
				factor);
	}

	public ElGamalProof1OfLC constructElGamalProof1OfL(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice, ElGamalCiphertext m,
			ElGamalReencryptFactor factor) {
		return constructElGamalProof1OfL.apply(key, ciphertexts, L, choice, m,
				factor);
	}

	@Use
	BruteForceDecode bruteForceDecode;

	/**
	 * @return The decoding of message m to a plaintext.
	 * @throws CryptoException If m does not decode to a plaintext i such that 1
	 *                         <= i <= L.
	 */
	@Override
	@Deprecated
	public int elGamal1OfLValue(ElGamalMsg m, int L, ElGamalParameters params)
			throws CryptoException {
		// it seems the parameters are the same throughout the vote
		// just make a list of the possible values and look it up
		ElGamalMsg mc = m;
		ElGamalParameters paramsc = params;
		// return the int value minus 1, since the well-known ciphertext list is
		// (1, 2, 3, ...), and we want to return the index of the value.
		return bruteForceDecode.apply(paramsc, mc.m, L) - 1;
	}

	/**
	 * Construct a well known ciphertext list. Needs to be coordinated with
	 * elGamal1OfLValue(ElGamalMsg) such that elGamal1OfLValue(m) = j where ret[j]
	 * = enc(m)
	 */
	@Override
	public CiphertextList constructWellKnownCiphertexts(ElGamalPublicKey key,
			int count) {
		return constructWellKnownCiphertexts.apply(key, count);
	}

	public CivitasBigInteger hashToBigInt(byte[] hash) {
		return convertHashToBigInt.apply(hash);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg) {
		return signAndEncrypt.apply(key, msg);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r) {
		return signAndEncrypt.apply(key, msg, r);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r, byte[] additionalEnv) {
		return signAndEncrypt.apply(key, msg, r, additionalEnv);
	}

	@Override
	public boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext) {
		return verifyElGamalSignature.apply(params, ciphertext);
	}

	@Override
	public boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv) {
		return verifyElGamalSignature.apply(params, ciphertext, additionalEnv);
	}

	@Override
	public ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoException {
		return decryptElGamalMessage.apply(key, (ElGamalCiphertext) ciphertext,
				additionalEnv);
	}

	@Override
	public ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalCiphertext ciphertext) throws CryptoException {
		return decryptElGamalMessage.apply(key, ciphertext);
	}

	@Override
	public ElGamalProofKnowDiscLog constructProofKnowDiscLog(
			ElGamalParameters prms, ElGamalPrivateKey k) {
		return constructProofKnowDiscLog.apply(prms, k);
	}

	@Override
	public PETShare constructPETShare(ElGamalParameters prms, ElGamalCiphertext a,
			ElGamalCiphertext b) {
		return constructPETShare.apply(prms, a, b);
	}

	@Deprecated
	public ElGamalMsg elGamalMsg(CivitasBigInteger m, ElGamalParameters params)
			throws CryptoException {
		throw new UnsupportedOperationException("use constructor");
	}

	@Override
	@Deprecated
	public ElGamalMsg elGamalMsg(int m, ElGamalParameters params)
			throws CryptoException {
		throw new UnsupportedOperationException("use constructor");
	}

	@Override
	@Deprecated
	public ElGamalMsg elGamalMsg(String m, ElGamalParameters params)
			throws CryptoException {
		throw new UnsupportedOperationException("use constructor");
	}

	@Override
	public ElGamalCiphertext elGamalCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalCiphertextFromXML.apply(r);
	}

	@Override
	public ElGamalParameters elGamalParametersFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalParametersFromXML.apply(r);
	}

	@Override
	public ElGamalPrivateKey elGamalPrivateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalPrivateKeyFromXML.apply(r);
	}

	@Override
	public ElGamalProofKnowDiscLog elGamalProofKnowDiscLogFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalProofKnowDiscLogFromXML.apply(r);
	}

	@Override
	public ElGamalPublicKey elGamalPublicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalPublicKeyFromXML.apply(r);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalSignedCiphertextFromXML.apply(r);
	}

	@Override
	public PETCommitment petCommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return petCommitmentFromXML.apply(r);
	}

	@Override
	public PETDecommitment petDecommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return petDecommitmentFromXML.apply(r);
	}

	@Override
	public ElGamalProofDiscLogEquality elGamalProofDiscLogEqualityFromXML(
			Reader r) throws IllegalArgumentException, IOException {
		return elGamalProofDiscLogEqualityFromXML.apply(r);
	}

	@Override
	public VoteCapability voteCapabilityFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return voteCapabilityFromXML.apply(r);
	}

	@Override
	public VoteCapabilityShare voteCapabilityShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return voteCapabilityShareFromXML.apply(r);
	}

	@Override
	public ElGamal1OfLReencryption elGamal1OfLReencryptionFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamal1OfLReencryptionFromXML.apply(r);
	}

	@Override
	public ElGamalProof1OfL elGamalProof1OfLFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalProof1OfLFromXML.apply(r);
	}

	@Override
	public PETShare petShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return petShareFromXML.apply(r);
	}

	@Override
	public ElGamalMsg combineDecryptionShares(ElGamalCiphertext c,
			ElGamalDecryptionShare[] shares, ElGamalParameters params)
			throws CryptoException {
		return combineDecryptionShares.apply(c, shares, params);
	}

	@Override
	public ElGamalCiphertext combinePETShareDecommitments(PETDecommitment[] decs,
			ElGamalParameters params) throws CryptoException {
		return combinePETShareDecommitments.apply(decs, params);
	}

	@Override
	public boolean petResult(ElGamalMsg petResult) {
		return isPetResult.apply(petResult);
	}

	@Override
	public ElGamalDecryptionShare constructDecryptionShare(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare) {
		return constructElGamalDecryptionShare.apply(c, keyShare);
	}

	@Override
	public ElGamalDecryptionShare decryptionShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return decryptionShareFromXML.apply(r);
	}

	@Override
	public ElGamalReencryptFactor elGamalReencryptFactorFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalReencryptFactorFromXML.apply(r);
	}

	@Override
	public PublicKeyCiphertext publicKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return publicKeyCiphertextFromXML.apply(r);
	}

	@Override
	public SharedKeyCiphertext sharedKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return sharedKeyCiphertextFromXML.apply(r);
	}

	@Override
	public SharedKey sharedKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return sharedKeyFromXML.apply(r);
	}

	@Override
	public SharedKey sharedKeyFromWire(Reader r)
			throws IllegalArgumentException, IOException {
		return sharedKeyFromWire.apply(r);
	}

	@Override
	public ElGamalProofDVR elGamalProofDVRFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalProofDVRFromXML.apply(r);
	}

	@Override
	public ElGamalPrivateKey egPrivKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPrivateKeyFromFile.apply(keyFile);
	}

	@Override
	public ElGamalPublicKey egPubKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPublicKeyFromFile.apply(keyFile);
	}

	@Override
	public PrivateKey privateKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return privatekeyFromFile.apply(keyFile);
	}

	@Override
	public PublicKey publicKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return publicKeyFromFile.apply(keyFile);
	}

	@Override
	public ElGamalKeyShare elGamalKeyShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalKeyShareFromXML.apply(r);
	}

	@Override
	public Signature signature(PrivateKey k, PublicKeyMsg msg)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		return signWithPublicKey.apply(k, msg);
	}

	@Override
	public Signature signature(PrivateKey k, byte[] bytes)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError {
		return signWithPublicKey.apply(k, bytes);
	}

	@Override
	public boolean publicKeyVerifySignature(PublicKey K, Signature s,
			PublicKeyMsg msg) {
		return verifyPublicKeySignature.apply(K, s, msg);
	}

	@Override
	public boolean publicKeyVerifySignature(PublicKey K, Signature s,
			byte[] bytes) {
		return verifyPublicKeySignature.apply(K, s, bytes);
	}

	@Override
	@Deprecated
	public PublicKeyMsg publicKeyVerifySignatureMsg(PublicKey K, Signature s,
			PublicKeyMsg msg) {
		return verifyPublicKeySignatureMsg.apply(K, s, msg);
	}

	@Override
	public Signature signatureFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return signatureFromXML.apply(r);
	}

	@Override
	public PublicKey publicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return publicKeyFromXML.apply(r);
	}

	@Override
	public PrivateKey privateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return privateKeyFromXML.apply(r);
	}

	@Override
	public ElGamalKeyShare constructKeyShare(ElGamalKeyPairShare kps) {
		return constructElGamalKeyShare.apply(kps);
	}

	@Override
	public ElGamalKeyShare elGamalKeyShare(ElGamalPublicKey K,
			ElGamalProofKnowDiscLog proof) {
		return constructElGamalKeyShare.apply(K, proof);
	}

	@Override
	public byte[] freshNonce(int bitlength) {
		return createFreshNonce.apply(bitlength);
	}

	@Override
	public MessageDigest messageDigest() {
		return obtainMessageDigest.apply();
	}

	@Override
	public byte[] messageDigest(byte[] a) {
		return computeMessageDigest.apply(a);
	}

	@Override
	@Deprecated
	public byte[] messageDigest(byte[] a, boolean constArray) {
		return computeMessageDigest.apply(a);
	}

	@Override
	public byte[] messageDigest(byte[] a, int i) {
		return computeMessageDigest.apply(a, i);
	}

	@Override
	@Deprecated
	public byte[] messageDigest(byte[] a, int i, boolean constArray) {
		return computeMessageDigest.apply(a, i);
	}

	@Override
	public byte[] messageDigest(String s) {
		return computeMessageDigest.apply(s);
	}

	@Override
	public int randomInt(int n) {
		return generateRandomInt.apply(n);
	}

	@Override
	public ElGamalProofDVR constructElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalCiphertext e,
			ElGamalCiphertext ePrime, ElGamalReencryptFactor er,
			ElGamalReencryptFactor erPrime) {
		return constructElGamalProofDVR.apply(k, verifierKey, e, ePrime, er,
				erPrime);
	}

	@Override
	public ElGamalProofDVR constructFakeElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertext e, ElGamalCiphertext ePrime) {
		return fakeElGamalProofDVRC.apply(k, verifierKey, verifierPrivKey, e,
				ePrime);
	}

	@Override
	public PublicKeyCiphertext publicKeyEncrypt(PublicKey key, PublicKeyMsg msg) {
		return encryptPublic.apply(key, msg);
	}

	@Override
	public PublicKeyMsg publicKeyDecrypt(PrivateKey key,
			PublicKeyCiphertext ciphertext)
			throws CryptoException, UnsupportedEncodingException, CryptoError {
		return decryptPublic.apply(key, ciphertext);
	}

	@Override
	public SharedKey generateSharedKey(int keyLength) {
		return generateSharedKey.apply(keyLength);
	}

	@Override
	public SharedKeyCiphertext sharedKeyEncrypt(SharedKey key, SharedKeyMsg msg) {
		return encryptShared.apply(key, msg);
	}

	@Override
	public SharedKeyMsg sharedKeyDecrypt(SharedKey key,
			SharedKeyCiphertext ciphertext)
			throws CryptoException, UnsupportedEncodingException, CryptoError {
		return decryptShared.apply(key, ciphertext);
	}

	@Deprecated
	public byte[] sharedKeyToBytes(SecretKey k) {
		return k.getEncoded();
	}

	public SecretKey sharedKeyFromBytes(byte[] bs) {
		return createSharedKeyFromBytes.apply(bs);
	}

	@Deprecated
	public byte[] publicKeyToBytes(java.security.PublicKey k) {
		return k.getEncoded();
	}

	@Deprecated
	public byte[] privateKeyToBytes(java.security.PrivateKey k) {
		return k.getEncoded();
	}

	public java.security.PublicKey publicKeyFromBytes(byte[] bs) {
		return createPublicKeyFromBytes.apply(bs);
	}

	public java.security.PrivateKey privateKeyFromBytes(byte[] bs) {
		return createPrivateKeyFromBytes.apply(bs);
	}

	@Override
	public String freshNonceBase64(int bitlength) {
		return createFreshNonceBase64.apply(bitlength);
	}

	@Override
	public String bytesToBase64(byte[] a) {
		return convertToBase64.apply(a);
	}

	@Override
	@Deprecated
	public String constBytesToBase64(byte[] a) {
		return convertToBase64.apply(a);
	}

	@Override
	@Deprecated
	public PublicKeyMsg publicKeyMsg(String m) throws CryptoException {
		throw new UnsupportedOperationException("Use constructor");
	}

	@Override
	@Deprecated
	public SharedKeyMsg sharedKeyMsg(String m) throws CryptoException {
		throw new UnsupportedOperationException("Use constructor");
	}

	public static String bigIntToString(CivitasBigInteger i) {
		return convertToBase64.apply(i);
	}

	public static CivitasBigInteger stringToBigInt(String s) {
		return convertToBigInt.apply(s);
	}

	@Override
	public ProofVote constructProofVote(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamal1OfLReencryption encChoice,
			String context, ElGamalReencryptFactor encCapabilityFactor,
			ElGamalReencryptFactor encChoiceFactor) {
		return constructProofVote.apply(params, encCapability, encChoice.m, context,
				encCapabilityFactor, encChoiceFactor);
	}

	@Override
	public ProofVote proofVoteFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return proofVoteFromXML.apply(r);
	}

}