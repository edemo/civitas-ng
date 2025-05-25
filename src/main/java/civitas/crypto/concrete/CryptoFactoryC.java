/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.CryptoFactory;
import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalKeyPair;
import civitas.crypto.ElGamalKeyPairShare;
import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPrivateKey;
import civitas.crypto.ElGamalProof1OfL;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.crypto.ElGamalProofKnowDiscLog;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.KeyPair;
import civitas.crypto.MessageDigest;
import civitas.crypto.PETCommitment;
import civitas.crypto.PETDecommitment;
import civitas.crypto.PETShare;
import civitas.crypto.PrivateKey;
import civitas.crypto.ProofVote;
import civitas.crypto.PublicKey;
import civitas.crypto.PublicKeyCiphertext;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.SharedKey;
import civitas.crypto.SharedKeyCiphertext;
import civitas.crypto.SharedKeyMsg;
import civitas.crypto.Signature;
import civitas.crypto.VoteCapability;
import civitas.crypto.VoteCapabilityShare;
import civitas.crypto.algorithms.CombineDecryptionShares;
import civitas.crypto.algorithms.CombineKeyShares;
import civitas.crypto.algorithms.CombinePETShareDecommitments;
import civitas.crypto.algorithms.CombineVoteCapabilityShares;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.ConstructElGamal1OfLReencryption;
import civitas.crypto.algorithms.ConstructElGamalDecryptionShare;
import civitas.crypto.algorithms.ConstructElGamalDiscLogEqualityProof;
import civitas.crypto.algorithms.ConstructElGamalProof1OfL;
import civitas.crypto.algorithms.ConstructElGamalProofDVRC;
import civitas.crypto.algorithms.ConstructPETShare;
import civitas.crypto.algorithms.ConstructProofKnowDiscLog;
import civitas.crypto.algorithms.ConstructProofVote;
import civitas.crypto.algorithms.ConstructWellKnownCiphertexts;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.ConvertToBase64;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.CreatePermutation;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.DecryptElGamalMessage;
import civitas.crypto.algorithms.ElGamalDecryptionShareFromXML;
import civitas.crypto.algorithms.ElGamalEncrypt;
import civitas.crypto.algorithms.ElGamalReencrypt;
import civitas.crypto.algorithms.FakeElGamalProofDVRC;
import civitas.crypto.algorithms.GenerateElGamalKeyPair;
import civitas.crypto.algorithms.GenerateElGamalParameters;
import civitas.crypto.algorithms.GenerateKeyPair;
import civitas.crypto.algorithms.GenerateKeyPairShare;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.algorithms.GenerateRandomInt;
import civitas.crypto.algorithms.GenerateVoteCapabilityShare;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.algorithms.GetRandomGenerator;
import civitas.crypto.algorithms.GetSharedKeyGenerator;
import civitas.crypto.algorithms.IsPetResult;
import civitas.crypto.algorithms.MultiplyCiphertexts;
import civitas.crypto.algorithms.ObtainMessageDigest;
import civitas.crypto.algorithms.SignAndEncrypt;
import civitas.crypto.algorithms.VerifyElGamalSignature;
import civitas.crypto.importing.ElGamal1OfLReencryptionFromXML;
import civitas.crypto.importing.ElGamalCiphertextFromXML;
import civitas.crypto.importing.ElGamalKeyShareFromXML;
import civitas.crypto.importing.ElGamalParametersFromXML;
import civitas.crypto.importing.ElGamalPrivateKeyFromFile;
import civitas.crypto.importing.ElGamalPrivateKeyFromXML;
import civitas.crypto.importing.ElGamalProof1OfLFromXML;
import civitas.crypto.importing.ElGamalProofDVRFromXML;
import civitas.crypto.importing.ElGamalProofDiscLogEqualityFromXML;
import civitas.crypto.importing.ElGamalProofKnowDiscLogFromXML;
import civitas.crypto.importing.ElGamalPublicKeyFromXML;
import civitas.crypto.importing.ElGamalReencryptFactorFromXML;
import civitas.crypto.importing.ElGamalSignedCiphertextFromXML;
import civitas.crypto.importing.PetCommitmentFromXML;
import civitas.crypto.importing.PetDecommitmentFromXML;
import civitas.crypto.importing.PetShareFromXML;
import civitas.crypto.importing.ProofVoteFromXML;
import civitas.crypto.importing.PublicKeyCiphertextFromXML;
import civitas.crypto.importing.SharedKeyCiphertextFromXML;
import civitas.crypto.importing.SharedKeyFromWire;
import civitas.crypto.importing.SharedKeyFromXML;
import civitas.crypto.importing.VoteCapabilityFromXML;
import civitas.crypto.importing.VoteCapabilityShareFromXML;
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
	ConstructElGamalProofDVRC constructElGamalProofDVRC;
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

	private SecretKeyFactory sharedKeyFactory;
	private KeyFactory publicKeyFactory;

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
		return generateElGamalParameters.apply(EL_GAMAL_KEY_LENGTH,
				EL_GAMAL_GROUP_LENGTH);
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
	public ElGamalCiphertext[] multiplyCiphertexts(
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
			ElGamalCiphertext[] ciphertexts, int L, int choice,
			ElGamalReencryptFactor factor) {
		return constructElGamal1OfLReencryption.apply(key, ciphertexts, L, choice,
				factor);
	}

	public ElGamalProof1OfLC constructElGamalProof1OfL(ElGamalPublicKeyC key,
			ElGamalCiphertext[] ciphertexts, int L, int choice, ElGamalCiphertextC m,
			ElGamalReencryptFactorC factor) {
		return constructElGamalProof1OfL.apply(key, ciphertexts, L, choice, m,
				factor);
	}

	/**
	 * @return The decoding of message m to a plaintext.
	 * @throws CryptoException If m does not decode to a plaintext i such that 1
	 *                         <= i <= L.
	 */
	@Override
	@Deprecated
	public int elGamal1OfLValue(ElGamalMsg m, int L, ElGamalParameters params)
			throws CryptoException {
		ElGamalMsgC mc = (ElGamalMsgC) m;
		ElGamalParametersC paramsc = (ElGamalParametersC) params;
		// return the int value minus 1, since the well-known ciphertext list is
		// (1, 2, 3, ...), and we want to return the index of the value.
		return paramsc.bruteForceDecode(mc.bigIntValue(), L) - 1;
	}

	/**
	 * Construct a well known ciphertext list. Needs to be coordinated with
	 * elGamal1OfLValue(ElGamalMsg) such that elGamal1OfLValue(m) = j where ret[j]
	 * = enc(m)
	 */
	@Override
	public ElGamalCiphertext[] constructWellKnownCiphertexts(ElGamalPublicKey key,
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
		return signAndEncrypt.apply(key, msg, r, null);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r, byte[] additionalEnv) {
		return signAndEncrypt.apply(key, msg, r, additionalEnv);
	}

	@Override
	public boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext) {
		return verifyElGamalSignature.apply(params, ciphertext, null);
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
		return decryptElGamalMessage.apply(key, ciphertext, additionalEnv);
	}

	@Override
	public ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalCiphertext ciphertext) throws CryptoException {
		return decryptElGamalMessage.apply(key, ciphertext, (byte[]) null);
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

	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	@Override
	public ElGamalProofKnowDiscLog elGamalProofKnowDiscLogFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalProofKnowDiscLogFromXML.apply(r);
	}

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;

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
		CivitasBigInteger d = ONE;
		return combinePETShareDecommitments.apply(decs, params, d);
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

	@Use
	ElGamalProofDVRFromXML elGamalProofDVRFromXML;

	@Override
	public ElGamalProofDVR elGamalProofDVRFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalProofDVRFromXML.apply(r);
	}

	@Use
	ElGamalPrivateKeyFromFile elGamalPrivateKeyFromFile;

	@Override
	public ElGamalPrivateKey egPrivKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPrivateKeyFromFile.apply(keyFile);
	}

	@Override
	public ElGamalPublicKey egPubKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPublicKeyFromXML
				.apply(new BufferedReader(new FileReader(keyFile)));
	}

	@Override
	public PrivateKey privateKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return PrivateKeyC.fromXML(new BufferedReader(new FileReader(keyFile)));
	}

	@Override
	public PublicKey publicKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return publicKeyFromXML(new BufferedReader(new FileReader(keyFile)));
	}

	@Use
	ElGamalKeyShareFromXML elGamalKeyShareFromXML;

	@Override
	public ElGamalKeyShare elGamalKeyShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return elGamalKeyShareFromXML.apply(r);
	}

	@Override
	public Signature signature(PrivateKey k, PublicKeyMsg msg) {
		try {
			PublicKeyMsgC mc = (PublicKeyMsgC) msg;
			byte[] bytes = messageDigest(mc.m.getBytes());
			return signature(k, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot sign", e);
		}
	}

	@Override
	public Signature signature(PrivateKey k, byte[] bytes) {
		try {
			java.security.Signature sig = java.security.Signature
					.getInstance(PUBLIC_KEY_SIGNATURE_ALG, PUBLIC_KEY_PROVIDER);
			PrivateKeyC kc = (PrivateKeyC) k;
			sig.initSign(kc.k);
			sig.update(bytes);
			return new SignatureC(sig.sign());
		} catch (InvalidKeyException e) {
			throw new CryptoError(e);
		} catch (SignatureException e) {
			throw new CryptoError(e);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError(e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot sign", e);
		}
	}

	@Override
	public boolean publicKeyVerifySignature(PublicKey K, Signature s,
			PublicKeyMsg msg) {
		try {
			PublicKeyMsgC mc = (PublicKeyMsgC) msg;
			byte[] bytes = messageDigest(mc.m.getBytes());
			return publicKeyVerifySignature(K, s, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	@Override
	public boolean publicKeyVerifySignature(PublicKey K, Signature s,
			byte[] bytes) {
		try {
			java.security.Signature sig = java.security.Signature
					.getInstance(PUBLIC_KEY_SIGNATURE_ALG, PUBLIC_KEY_PROVIDER);
			PublicKeyC Kc = (PublicKeyC) K;
			SignatureC sc = (SignatureC) s;
			sig.initVerify(Kc.k);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (InvalidKeyException e) {
			throw new CryptoError(e);
		} catch (SignatureException e) {
			throw new CryptoError(e);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError(e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	@Override
	public PublicKeyMsg publicKeyVerifySignatureMsg(PublicKey K, Signature s,
			PublicKeyMsg msg) {
		if (publicKeyVerifySignature(K, s, msg)) {
			return msg;
		}
		return null;
	}

	@Override
	public Signature signatureFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return SignatureC.fromXML(r);
	}

	@Override
	public PublicKey publicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PublicKeyC.fromXML(r);
	}

	@Override
	public PrivateKey privateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PrivateKeyC.fromXML(r);
	}

	@Override
	public ElGamalKeyShare constructKeyShare(ElGamalKeyPairShare kps) {
		ElGamalKeyShare egks = elGamalKeyShare(kps.pubKey,
				constructProofKnowDiscLog(kps.pubKey.getParams(), kps.privKey));
		if (!egks.verify()) {
			throw new CryptoError("Cannot verify a newly created key share!");
		}
		return egks;
	}

	@Override
	public ElGamalKeyShare elGamalKeyShare(ElGamalPublicKey K,
			ElGamalProofKnowDiscLog proof) {
		if (K instanceof ElGamalPublicKeyC
				&& proof instanceof ElGamalProofKnowDiscLogC) {
			return new ElGamalKeyShareC((ElGamalPublicKeyC) K,
					(ElGamalProofKnowDiscLogC) proof);
		}
		throw new Error("problem with parameters");
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
		MessageDigest md = messageDigest();
		md.update(a);
		return md.digest();
	}

	@Override
	public byte[] messageDigest(byte[] a, boolean constArray) {
		return messageDigest(a);
	}

	@Override
	public byte[] messageDigest(byte[] a, int i) {
		MessageDigest md = messageDigest();
		md.update(a);
		md.update(i);
		return md.digest();
	}

	@Override
	public byte[] messageDigest(byte[] a, int i, boolean constArray) {
		return messageDigest(a, i, constArray);
	}

	@Override
	public byte[] messageDigest(String s) {
		MessageDigest md = messageDigest();
		md.update(s);
		return md.digest();
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
		try {
			ElGamalParametersC ps = (ElGamalParametersC) k.getParams();
			CivitasBigInteger zeta = ((ElGamalReencryptFactorC) erPrime).r
					.modSubtract(((ElGamalReencryptFactorC) er).r, ps.q);
			return constructElGamalProofDVRC.apply((ElGamalCiphertextC) e,
					(ElGamalCiphertextC) ePrime, (ElGamalPublicKeyC) k,
					(ElGamalPublicKeyC) verifierKey, zeta);
		} catch (ClassCastException ex) {
			return null;
		}
	}

	@Override
	public ElGamalProofDVR constructFakeElGamalProofDVR(ElGamalPublicKey k,
			ElGamalPublicKey verifierKey, ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertext e, ElGamalCiphertext ePrime) {
		try {
			return fakeElGamalProofDVRC.apply((ElGamalCiphertextC) e,
					(ElGamalCiphertextC) ePrime, (ElGamalPublicKeyC) k,
					(ElGamalPublicKeyC) verifierKey,
					(ElGamalPrivateKeyC) verifierPrivKey);
		} catch (ClassCastException ex) {
			return null;
		}
	}

	@Override
	public PublicKeyCiphertext publicKeyEncrypt(PublicKey key, PublicKeyMsg msg) {
		PublicKeyC keyc = (PublicKeyC) key;
		PublicKeyMsgC msgc = (PublicKeyMsgC) msg;
		byte[] encrypted = jseCrypt(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER,
				keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new PublicKeyCiphertextC(encrypted);
	}

	@Override
	public PublicKeyMsg publicKeyDecrypt(PrivateKey key,
			PublicKeyCiphertext ciphertext) throws CryptoException {
		PrivateKeyC keyc = (PrivateKeyC) key;
		PublicKeyCiphertextC ciphertextc = (PublicKeyCiphertextC) ciphertext;
		byte[] plaintext = jseCrypt(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER,
				keyc.k, Cipher.DECRYPT_MODE, ciphertextc.toBytes());
		return new PublicKeyMsgC(plaintext);
	}

	@Override
	public SharedKey generateSharedKey(int keyLength) {
		SecretKey k = getSharedKeyGenerator.apply(keyLength).generateKey();
		return new SharedKeyC(k, "sharedKey-civitas");
	}

	@Override
	public SharedKeyCiphertext sharedKeyEncrypt(SharedKey key, SharedKeyMsg msg) {
		SharedKeyC keyc = (SharedKeyC) key;
		SharedKeyMsgC msgc = (SharedKeyMsgC) msg;
		byte[] encrypted = jseCrypt(SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER,
				keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new SharedKeyCiphertextC(encrypted);
	}

	@Override
	public SharedKeyMsg sharedKeyDecrypt(SharedKey key,
			SharedKeyCiphertext ciphertext) throws CryptoException {
		SharedKeyC keyc = (SharedKeyC) key;
		SharedKeyCiphertextC ciphertextc = (SharedKeyCiphertextC) ciphertext;
		byte[] plaintext = jseCrypt(SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER,
				keyc.k, Cipher.DECRYPT_MODE, ciphertextc.toBytes());
		return new SharedKeyMsgC(plaintext);
	}

	private byte[] jseCrypt(String alg, String provider, Key skey, int mode,
			byte[] input) {

		// Instantiate the cipher
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(alg, provider);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError("Cannot find algorithm " + alg, e);
		} catch (NoSuchPaddingException e) {
			throw new CryptoError("Cannot find algorithm " + alg, e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + provider, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create cipher", e);
		}

		try {
			cipher.init(mode, skey);
		} catch (InvalidKeyException e) {
			throw new CryptoError(
					"Invalid key.  May need to install unlimited strength crypto policies.",
					e);
		}

		byte[] output;
		try {
			output = cipher.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			throw new CryptoError("Illegal block size", e);
		} catch (BadPaddingException e) {
			throw new CryptoError("bad padding", e);
		} catch (RuntimeException e) {
			throw new CryptoError(e);
		}
		return output;
	}

	@Deprecated
	public byte[] sharedKeyToBytes(SecretKey k) {
		return k.getEncoded();
	}

	public SecretKey sharedKeyFromBytes(byte[] bs) {
		SecretKeySpec skeySpec = new SecretKeySpec(bs, SHARED_KEY_ALG);
		try {
			return sharedKeyFactory.generateSecret(skeySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

	public byte[] publicKeyToBytes(java.security.PublicKey k) {
		return k.getEncoded();
	}

	@Deprecated
	public byte[] privateKeyToBytes(java.security.PrivateKey k) {
		return k.getEncoded();
	}

	public java.security.PublicKey publicKeyFromBytes(byte[] bs) {
		KeySpec keySpec = new X509EncodedKeySpec(bs);
		try {
			return publicKeyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

	public java.security.PrivateKey privateKeyFromBytes(byte[] bs) {
		KeySpec keySpec = new PKCS8EncodedKeySpec(bs);
		try {
			return publicKeyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
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
	public PublicKeyMsg publicKeyMsg(String m) throws CryptoException {
		return new PublicKeyMsgC(m);
	}

	@Override
	public SharedKeyMsg sharedKeyMsg(String m) throws CryptoException {
		return new SharedKeyMsgC(m);
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
		try {
			return constructProofVote.apply((ElGamalParametersC) params,
					(ElGamalCiphertextC) encCapability,
					((ElGamal1OfLReencryptionC) encChoice).m, context,
					(ElGamalReencryptFactorC) encCapabilityFactor,
					(ElGamalReencryptFactorC) encChoiceFactor);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ProofVote proofVoteFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return proofVoteFromXML.apply(r);
	}

}