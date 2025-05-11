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
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import civitas.common.Util;
import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.CryptoFactory;
import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalKeyPair;
import civitas.crypto.ElGamalKeyPairImpl;
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
import civitas.util.CivitasBigInteger;

public class CryptoFactoryC implements CryptoFactory {
	private final boolean DEBUG = false;

	private final int BIT_SECURITY_80 = 1;
	private final int BIT_SECURITY_112 = 2;
	private final int BIT_SECURITY_128 = 3;
	private final int BIT_SECURITY_CCS07 = 0;

	private final int BIT_SECURITY = BIT_SECURITY_80;

	/*
	 * The following constants define the algorithms and providers to use.
	 */
	private final String MESSAGE_DIGEST_ALG = "SHA-256";
	private final String MESSAGE_DIGEST_PROVIDER = null; // use any provider

	private final String SHARED_KEY_ALG = "AES";
	private final String SHARED_KEY_CIPHER_ALG = "AES"; // "AES/CBC/PKCS7Padding";
	private final String SHARED_KEY_PROVIDER = "BC";

	private final String PUBLIC_KEY_ALG = "RSA";

	private final String PUBLIC_KEY_CIPHER_ALG = "RSA/ECB/PKCS1Padding";
//  private final String PUBLIC_KEY_CIPHER_ALG = "RSA/NONE/OAEPPADDING";
	private final String PUBLIC_KEY_SIGNATURE_ALG = "SHA512WithRSAEncryption";
	private final String PUBLIC_KEY_PROVIDER = "BC";

	private int EL_GAMAL_GROUP_LENGTH; // size in bits for p
	private int EL_GAMAL_KEY_LENGTH; // size in bits for q

	private Map<String, KeyGenerator> sharedKeyGenerators = new HashMap<String, KeyGenerator>();
	private Map<String, KeyPairGenerator> publicKeyGenerators = new HashMap<String, KeyPairGenerator>();
	private SecretKeyFactory sharedKeyFactory;
	private KeyFactory publicKeyFactory;

	// count the number of operations
	private static long numPublicKeyEncs = 0;
	private static long numPublicKeyDecs = 0;
	private static long numPublicKeySign = 0;
	private static long numPublicKeyVerifySig = 0;
	private static long numSharedKeyEncs = 0;
	private static long numSharedKeyDecs = 0;
	private static long numElGamalEncs = 0;
	private static long numElGamalReencs = 0;
	private static long numElGamalDecs = 0;
	private static long numElGamalDecShare = 0;
	private static long numElGamalSignedEncs = 0;
	private static long numElGamalVerifies = 0;

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

	private static final CryptoFactoryC singleton = new CryptoFactoryC();

	public static CryptoFactoryC singleton() {
		return singleton;
	}

	private CryptoFactoryC() {
		setBitSecurity();
		initializeCryptoProviders();
	}

	/**
	 * Initialize the fields of this class that define bit security. I.e., the
	 * sizes of keys and nonces.
	 */
	private void setBitSecurity() {
		// TODO: need to initialize nonce sizes too.

		switch (BIT_SECURITY) {
		/*
		 * Original CCS submission values. Not a consistent level of security.
		 */
		case BIT_SECURITY_CCS07:
			EL_GAMAL_KEY_LENGTH = 1024;
			EL_GAMAL_GROUP_LENGTH = 1025; // not originally used
			break;

		/*
		 * Note: the remaining cases set the shared key length to 128. This is
		 * generally higher than the bits of security would suggest. The code does
		 * this because 128 is the minimum size for AES, which is the assumed shared
		 * key cipher. For 80 bits of security, e.g., it would be possible to
		 * instead use 3DES.
		 */

		/* 80 bits of security - NIST says use 2007 to 2010 */
		case BIT_SECURITY_80:
		default:
			EL_GAMAL_KEY_LENGTH = 160;
			EL_GAMAL_GROUP_LENGTH = 1024;
			break;

		/* 112 bits of security - NIST says use 2011 to 2030 */
		case BIT_SECURITY_112:
			EL_GAMAL_KEY_LENGTH = 224;
			EL_GAMAL_GROUP_LENGTH = 2048;
			break;

		/* 128 bits of security - NIST says use > 2030 */
		case BIT_SECURITY_128:
			EL_GAMAL_KEY_LENGTH = 256;
			EL_GAMAL_GROUP_LENGTH = 3072;
			break;
		}
	}

	/**
	 * Get an appropriate public key generator, creating one if necessary.
	 */
	protected KeyPairGenerator publicKeyGenerator(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyPairGenerator g = publicKeyGenerators.get(genKey);
		if (g != null)
			return g;
		// need to create the public key generator
		try {
			g = KeyPairGenerator.getInstance(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER);
			g.initialize(keyLength);
			publicKeyGenerators.put(genKey, g);
			return g;
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(
					"Cannot find public key algorithm " + PUBLIC_KEY_ALG);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + PUBLIC_KEY_PROVIDER, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create key pair generator and/or factory",
					e);
		}
	}

	/**
	 * Get an appropriate shared key generator, creating one if necessary.
	 */
	protected KeyGenerator sharedKeyGenerator(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyGenerator g = sharedKeyGenerators.get(genKey);
		if (g != null)
			return g;
		// need to create the shared key generator
		try {
			g = KeyGenerator.getInstance(SHARED_KEY_ALG, SHARED_KEY_PROVIDER);
			g.init(keyLength);
			sharedKeyGenerators.put(genKey, g);
			return g;
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(
					"Cannot find shared key algorithm " + SHARED_KEY_ALG, e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + SHARED_KEY_PROVIDER, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create key generator", e);
		}
	}

	/**
	 * Initialize the crypto providers.
	 *
	 */
	private void initializeCryptoProviders() {
		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance(SHARED_KEY_ALG, SHARED_KEY_PROVIDER);
		} catch (NoSuchAlgorithmException e) {
			// this secret key alg does not need a key factory.
			skf = null;
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + SHARED_KEY_PROVIDER, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create key factory", e);
		}
		sharedKeyFactory = skf;

		try {
			publicKeyFactory = KeyFactory.getInstance(PUBLIC_KEY_ALG,
					PUBLIC_KEY_PROVIDER);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(
					"Cannot find public key algorithm " + PUBLIC_KEY_ALG);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + PUBLIC_KEY_PROVIDER, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create key pair generator and/or factory",
					e);
		}
	}

	/*
	 * The following methods must return implementation specific KeySpecs.
	 */
	private SecretKeySpec secretKeyAlgKeySpec(byte[] bs) {
		return new SecretKeySpec(bs, SHARED_KEY_ALG);
	}

	@Override
	public int[] createPermutation(int size) {
		List<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			l.add(Integer.valueOf(i));
		}

		// now select and remove elements at random from the list.
		int[] perm = new int[size];
		for (int i = 0; i < size; i++) {
			int j = randomInt(l.size());
			perm[i] = l.remove(j).intValue();
		}

		return perm;
	}

	/** Generate a Schnorr prime group */
	@Override
	public ElGamalParameters generateElGamalParameters(int keyLength,
			int groupLength) {
		return new ElGamalParametersC(keyLength, groupLength);
	}

	/** Generate a safe prime group */
	public ElGamalParameters generateElGamalParameters(int keyLength) {
		return new ElGamalParametersC(keyLength, keyLength + 1);
	}

	@Override
	public ElGamalParameters generateElGamalParameters() {
		return generateElGamalParameters(EL_GAMAL_KEY_LENGTH,
				EL_GAMAL_GROUP_LENGTH);
	}

	@Override
	public ElGamalKeyPair generateElGamalKeyPair(ElGamalParameters p) {
		ElGamalParametersC ps = (ElGamalParametersC) p;
		CivitasBigInteger x = CryptoAlgs.randomElement(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);
		ElGamalPrivateKeyC k = new ElGamalPrivateKeyC(x, ps);
		ElGamalPublicKeyC K = new ElGamalPublicKeyC(y, ps);
		return new ElGamalKeyPairImpl(K, k);
	}

	@Override
	public KeyPair generateKeyPair(int keyLength) {
		java.security.KeyPair kp = publicKeyGenerator(keyLength).generateKeyPair();
		java.security.PublicKey pubk = kp.getPublic();
		java.security.PrivateKey prvk = kp.getPrivate();

		return new KeyPair(new PublicKeyC(pubk, "keypair-" + freshNonceBase64(64)),
				new PrivateKeyC(prvk));
	}

	@Override
	public ElGamalKeyPairShare generateKeyPairShare(ElGamalParameters params) {
		// The zero knowledge proof is constructed later, in the call to
		// constructKeyShare
		ElGamalParametersC ps = (ElGamalParametersC) params;

		// choose x in Z_q at random. This is the share of the private key.
		CivitasBigInteger x = CryptoAlgs.randomElement(ps.q);
		// the public part of the key is y
		CivitasBigInteger y = ps.g.modPow(x, ps.p);

		ElGamalPublicKey pub = new ElGamalPublicKeyC(y, params);
		ElGamalPrivateKey priv = new ElGamalPrivateKeyC(x, params);
		return new ElGamalKeyPairShare(params, pub, priv);
	}

	@Override
	public VoteCapabilityShare generateVoteCapabilityShare(ElGamalParameters p) {
		ElGamalParametersC ps = (ElGamalParametersC) p;
		CivitasBigInteger x = CryptoAlgs.randomElement(ps.q);
		try {
			return new VoteCapabilityShareC(x, ps);
		} catch (CryptoException imposs) {
			throw new CryptoError(imposs);
		}
	}

	@Override
	public VoteCapability[] combineVoteCapabilityShares(
			VoteCapabilityShare[][] shares, ElGamalParameters p) {
		if (shares == null)
			return null;
		try {
			ElGamalParametersC params = (ElGamalParametersC) p;
			// multiply all the shares together
			CivitasBigInteger[] accum = new CivitasBigInteger[shares[0].length];
			for (int i = 0; i < shares.length; i++) {
				for (int j = 0; j < shares[i].length; j++) {
					VoteCapabilityShareC s = (VoteCapabilityShareC) shares[i][j];
					if (accum[j] == null) {
						accum[j] = s.m;
					} else {
						accum[j] = accum[j].modMultiply(s.m, params.p);
					}
				}
			}
			VoteCapability[] ret = new VoteCapability[accum.length];
			for (int j = 0; j < accum.length; j++) {
				ret[j] = new VoteCapabilityC(accum[j]);
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public ElGamalCiphertext[] multiplyCiphertexts(
			ElGamalSignedCiphertext[][] ciphertexts, ElGamalParameters p) {
		if (ciphertexts == null)
			return null;
		try {
			ElGamalParametersC params = (ElGamalParametersC) p;
			// multiply all the shares together
			CivitasBigInteger[] aAccum = new CivitasBigInteger[ciphertexts[0].length];
			CivitasBigInteger[] bAccum = new CivitasBigInteger[ciphertexts[0].length];
			for (int i = 0; i < ciphertexts.length; i++) {
				for (int j = 0; j < ciphertexts[i].length; j++) {
					ElGamalCiphertextC s = (ElGamalCiphertextC) ciphertexts[i][j];
					if (aAccum[j] == null) {
						aAccum[j] = s.a;
						bAccum[j] = s.b;
					} else {
						aAccum[j] = aAccum[j].modMultiply(s.a, params.p);
						bAccum[j] = bAccum[j].modMultiply(s.b, params.p);
					}
				}
			}
			ElGamalCiphertext[] ret = new ElGamalCiphertext[aAccum.length];
			for (int j = 0; j < aAccum.length; j++) {
				ret[j] = new ElGamalCiphertextC(aAccum[j], bAccum[j]);
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public ElGamalPublicKey combineKeyShares(ElGamalKeyShare[] shares)
			throws CryptoException {
		if (shares == null)
			return null;
		CivitasBigInteger accum = CivitasBigInteger.ONE;
		ElGamalParameters params = null;
		for (int i = 0; i < shares.length; i++) {
			ElGamalKeyShare s = shares[i];

			// Check the proofs that this is a valid share
			try {
				if (params == null) {
					params = s.pubKey().getParams();
				}
				if (!s.verify()) {
					throw new CryptoException("Invalid share");
				}
			} catch (NullPointerException e) {
				throw new CryptoException("Invalid share or proof");
			}
			// accumulate the keys..
			if (s.pubKey() instanceof ElGamalPublicKeyC) {
				accum = accum.multiply(((ElGamalPublicKeyC) s.pubKey()).y);
			}
		}
		return new ElGamalPublicKeyC(accum, params);
	}

	@Override
	public ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg) {
		try {
			numElGamalEncs++;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger r = CryptoAlgs.randomElement(ps.q);
			CivitasBigInteger a = ps.g.modPow(r, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalCiphertext elGamalEncrypt(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor encryptFactor) {
		try {
			numElGamalEncs++;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger r = ((ElGamalReencryptFactorC) encryptFactor).r;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger a = ps.g.modPow(r, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext) {
		try {
			numElGamalReencs++;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			ElGamalCiphertextC c = (ElGamalCiphertextC) ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger r = CryptoAlgs.randomElement(ps.q);
			a = a.modMultiply(ps.g.modPow(r, ps.p), ps.p);
			b = b.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalReencryptFactor generateElGamalReencryptFactor(
			ElGamalParameters params) {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) params;
			return new ElGamalReencryptFactorC(CryptoAlgs.randomElement(ps.q));
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}

	}

	@Override
	public ElGamalCiphertext elGamalReencrypt(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext, ElGamalReencryptFactor factor) {
		try {
			numElGamalReencs++;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			ElGamalCiphertextC c = (ElGamalCiphertextC) ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger r = ((ElGamalReencryptFactorC) factor).r;
			a = a.modMultiply(ps.g.modPow(r, ps.p), ps.p);
			b = b.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamal1OfLReencryption elGamal1OfLReencrypt(ElGamalPublicKey key,
			ElGamalCiphertext[] ciphertexts, int L, int choice,
			ElGamalReencryptFactor factor) {
		if (ciphertexts == null || choice >= L || L > ciphertexts.length) {
			return null;
		}
		ElGamalCiphertextC m = (ElGamalCiphertextC) elGamalReencrypt(key,
				ciphertexts[choice], factor);
		ElGamalProof1OfLC proof = constructElGamalProof1OfL((ElGamalPublicKeyC) key,
				ciphertexts, L, choice, m, (ElGamalReencryptFactorC) factor);
		return new ElGamal1OfLReencryptionC(m, proof);
	}

	private ElGamalProof1OfLC constructElGamalProof1OfL(ElGamalPublicKeyC key,
			ElGamalCiphertext[] ciphertexts, int L, int choice, ElGamalCiphertextC m,
			ElGamalReencryptFactorC factor) {
		return ElGamalProof1OfLC.constructProof(key, ciphertexts, L, choice, m,
				factor);
	}

	/**
	 * @return The decoding of message m to a plaintext.
	 * @throws CryptoException If m does not decode to a plaintext i such that 1
	 *                         <= i <= L.
	 */
	@Override
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
		if (count < 0 || key == null)
			return null;
		ElGamalCiphertext[] cs = new ElGamalCiphertext[count];

		// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...
		// using the encryption factor 0. This is assumed by some of the
		// zero knowledge proofs.
		ElGamalReencryptFactor factor = new ElGamalReencryptFactorC(
				CivitasBigInteger.ZERO);
		try {
			ElGamalParametersC params = (ElGamalParametersC) key.getParams();
			for (int i = 0; i < count; i++) {
				// encrypt (i+1);
				try {
					cs[i] = elGamalEncrypt(key, new ElGamalMsgC(i + 1, params), factor);
				} catch (CryptoException imposs) {
					throw new CryptoError(imposs);
				}
			}
		} catch (ClassCastException e) {
			return null;
		}
		return cs;
	}

	/**
	 * Convert a hash (or rather, an arbitrary byte array) to an element from the
	 * group defined by the El Gamal parameters.
	 */
	CivitasBigInteger hashToBigInt(byte[] hash) {
		// Force the hash to be positive.
		CivitasBigInteger x = new CivitasBigInteger(1, hash);
		return x;
	}

	/**
	 * Compute a hash over a list of CivitasBigIntegers.
	 */
	byte[] hash(List<CivitasBigInteger> l) {
		// Compute the hash by updating a message digest
		// with the byte representation of the big ints.
		MessageDigest md = messageDigest();
		for (Iterator<CivitasBigInteger> iter = l.iterator(); iter.hasNext();) {
			CivitasBigInteger i = iter.next();
			md.update(i.toByteArray());
		}
		return md.digest();
	}

	CivitasBigInteger hash(CivitasBigInteger a, CivitasBigInteger b) {
		return hash(a, b, null);
	}

	CivitasBigInteger hash(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c) {
		return hash(a, b, c, null);
	}

	CivitasBigInteger hash(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, byte[] d) {
		// Compute the hash by updating a message digest
		// with the byte representation of the big ints.
		MessageDigest md = messageDigest();
		if (a != null)
			md.update(a.toByteArray());
		if (b != null)
			md.update(b.toByteArray());
		if (c != null)
			md.update(c.toByteArray());
		if (d != null)
			md.update(d);
		return hashToBigInt(md.digest());
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg) {
		return elGamalSignedEncrypt(key, msg,
				this.generateElGamalReencryptFactor(key.getParams()));
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r) {
		return elGamalSignedEncrypt(key, msg, r, null);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedEncrypt(ElGamalPublicKey key,
			ElGamalMsg msg, ElGamalReencryptFactor r, byte[] additionalEnv) {
		try {
			numElGamalSignedEncs++;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger rr = ((ElGamalReencryptFactorC) r).r;
			CivitasBigInteger s = CryptoAlgs.randomElement(ps.q);
			CivitasBigInteger a = ps.g.modPow(rr, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(rr, ps.p), ps.p);

			CivitasBigInteger c = hash(ps.g.modPow(s, ps.p), a, b, additionalEnv)
					.mod(ps.q); // hash of (g^s,g^r,my^r) == (g^s, a, b)
			CivitasBigInteger d = s.modAdd(c.modMultiply(rr, ps.q), ps.q);
			return new ElGamalSignedCiphertextC(a, b, c, d);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext) {
		return elGamalVerify(params, ciphertext, null);
	}

	@Override
	public boolean elGamalVerify(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv) {
		try {
			numElGamalVerifies++;
			ElGamalParametersC ps = (ElGamalParametersC) params;
			ElGamalSignedCiphertextC cc = (ElGamalSignedCiphertextC) ciphertext;
			// to verify, check that c == h(g^d * a^(-c), a, b)
			CivitasBigInteger x = ps.g.modPow(cc.d.mod(ps.q), ps.p)
					.modMultiply(cc.a.modPow(cc.c.modNegate(ps.q), ps.p), ps.p);
			CivitasBigInteger v = hash(x, cc.a, cc.b, additionalEnv).mod(ps.q);
			return cc.c.equals(v);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalCiphertext ciphertext) throws CryptoException {
		return elGamalDecryptImpl(key, ciphertext, null);
	}

	private ElGamalMsg elGamalDecryptImpl(ElGamalPrivateKey key,
			ElGamalCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoException {
		try {
			numElGamalDecs++;
			ElGamalPrivateKeyC k = (ElGamalPrivateKeyC) key;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();

			if (ciphertext instanceof ElGamalSignedCiphertext) {
				if (!elGamalVerify(ps, (ElGamalSignedCiphertext) ciphertext,
						additionalEnv)) {
					throw new CryptoException("Ciphertext failed verification");
				}
			}
			ElGamalCiphertextC c = (ElGamalCiphertextC) ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger m = b.modDivide(a.modPow(k.x, ps.p), ps.p);
			return new ElGamalMsgC(m);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		} catch (NullPointerException e) {
			throw new CryptoError(e);
		}

	}

	@Override
	public ElGamalMsg elGamalDecrypt(ElGamalPrivateKey key,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoException {
		return elGamalDecryptImpl(key, ciphertext, additionalEnv);
	}

	@Override
	public ElGamalProofKnowDiscLog constructProofKnowDiscLog(
			ElGamalParameters prms, ElGamalPrivateKey k) {
		if (k == null || !(k instanceof ElGamalPrivateKeyC)) {
			if (DEBUG)
				Thread.dumpStack();
			return null;
		}
		if (prms == null || !(prms instanceof ElGamalParametersC)) {
			if (DEBUG)
				Thread.dumpStack();
			return null;
		}
		ElGamalParametersC params = (ElGamalParametersC) prms;
		CivitasBigInteger x = ((ElGamalPrivateKeyC) k).x;
		try {
			CivitasBigInteger v = params.g.modPow(x, params.p);
			CivitasBigInteger z = CryptoAlgs.randomElement(params.q);
			CivitasBigInteger a = params.g.modPow(z, params.p);
			CivitasBigInteger c = hash(v, a).mod(params.q); // can take mod q without
																											// any ill effects.
			CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);
			return new ElGamalProofKnowDiscLogC(a, c, r, v);
		} catch (RuntimeException e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
	}

	@Override
	public PETShare constructPETShare(ElGamalParameters prms, ElGamalCiphertext a,
			ElGamalCiphertext b) {
		if (a == null || !(a instanceof ElGamalCiphertextC))
			return null;
		if (b == null || !(b instanceof ElGamalCiphertextC))
			return null;
		if (prms == null || !(prms instanceof ElGamalParametersC))
			return null;
		ElGamalParametersC params = (ElGamalParametersC) prms;
		ElGamalCiphertextC ac = (ElGamalCiphertextC) a;
		ElGamalCiphertextC bc = (ElGamalCiphertextC) b;

		try {
			CivitasBigInteger z = CryptoAlgs.randomElement(params.q);
			return new PETShareC(ac, bc, z);
		} catch (RuntimeException e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
	}

	public ElGamalMsg elGamalMsg(CivitasBigInteger m, ElGamalParameters params)
			throws CryptoException {
		try {
			return new ElGamalMsgC(m, (ElGamalParametersC) params);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalMsg elGamalMsg(int m, ElGamalParameters params)
			throws CryptoException {
		try {
			return new ElGamalMsgC(m, (ElGamalParametersC) params);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalMsg elGamalMsg(String m, ElGamalParameters params)
			throws CryptoException {
		try {
			return new ElGamalMsgC(m, (ElGamalParametersC) params);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalCiphertext elGamalCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		if (Util.isNextTag(r, ElGamalCiphertext.OPENING_TAG)) {
			return ElGamalCiphertextC.fromXML(r);
		} else {
			return elGamalSignedCiphertextFromXML(r);
		}
	}

	@Override
	public ElGamalParameters elGamalParametersFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalParametersC.fromXML(r);
	}

	@Override
	public ElGamalPrivateKey elGamalPrivateKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalPrivateKeyC.fromXML(r);
	}

	@Override
	public ElGamalProofKnowDiscLog elGamalProofKnowDiscLogFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalProofKnowDiscLogC.fromXML(r);
	}

	@Override
	public ElGamalPublicKey elGamalPublicKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalPublicKeyC.fromXML(r);
	}

	@Override
	public ElGamalSignedCiphertext elGamalSignedCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalSignedCiphertextC.fromXMLsub(r);
	}

	@Override
	public PETCommitment petCommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PETCommitmentC.fromXML(r);
	}

	@Override
	public PETDecommitment petDecommitmentFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PETDecommitmentC.fromXML(r);
	}

	@Override
	public ElGamalProofDiscLogEquality elGamalProofDiscLogEqualityFromXML(
			Reader r) throws IllegalArgumentException, IOException {
		return ElGamalProofDiscLogEqualityC.fromXML(r);
	}

	@Override
	public VoteCapability voteCapabilityFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return VoteCapabilityC.fromXML(r);
	}

	@Override
	public VoteCapabilityShare voteCapabilityShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return VoteCapabilityShareC.fromXML(r);
	}

	@Override
	public ElGamal1OfLReencryption elGamal1OfLReencryptionFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamal1OfLReencryptionC.fromXML(r);
	}

	@Override
	public ElGamalProof1OfL elGamalProof1OfLFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalProof1OfLC.fromXML(r);
	}

	@Override
	public PETShare petShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PETShareC.fromXML(r);
	}

	@Override
	public ElGamalMsg combineDecryptionShares(ElGamalCiphertext c,
			ElGamalDecryptionShare[] shares, ElGamalParameters params)
			throws CryptoException {
		CivitasBigInteger prod = CivitasBigInteger.ONE;
		try {
			ElGamalCiphertextC cipher = (ElGamalCiphertextC) c;
			ElGamalParametersC ps = (ElGamalParametersC) params;
			for (int i = 0; i < shares.length; i++) {
				ElGamalDecryptionShareC share = (ElGamalDecryptionShareC) shares[i];
				prod = prod.modMultiply(share.ai, ps.p);
			}
			CivitasBigInteger m = cipher.b.modDivide(prod, ps.p);
			return new ElGamalMsgC(m);
		} catch (RuntimeException e) {
			throw new CryptoError(e);
		}
	}

	@Override
	public ElGamalCiphertext combinePETShareDecommitments(PETDecommitment[] decs,
			ElGamalParameters params) throws CryptoException {
		CivitasBigInteger d = CivitasBigInteger.ONE;
		CivitasBigInteger e = CivitasBigInteger.ONE;
		ElGamalParametersC ps = (ElGamalParametersC) params;

		for (int i = 0; i < (decs == null ? 0 : decs.length); i++) {
			PETDecommitmentC decom = (PETDecommitmentC) decs[i];
			d = d.modMultiply(decom.di, ps.p);
			e = e.modMultiply(decom.ei, ps.p);
		}
		return new ElGamalCiphertextC(d, e);
	}

	@Override
	public boolean petResult(ElGamalMsg petResult) {
		// Pet result is true if the message == 1
		if (petResult instanceof ElGamalMsgC) {
			ElGamalMsgC m = (ElGamalMsgC) petResult;
			return CivitasBigInteger.ONE.equals(m.m);
		}
		return false;
	}

	@Override
	public ElGamalDecryptionShare constructDecryptionShare(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare) {
		if (c instanceof ElGamalCiphertextC
				&& keyShare.privKey instanceof ElGamalPrivateKeyC
				&& keyShare.privKey.getParams() instanceof ElGamalParametersC) {
			try {
				numElGamalDecShare++;
				ElGamalCiphertextC mc = (ElGamalCiphertextC) c;
				ElGamalPrivateKeyC priv = (ElGamalPrivateKeyC) keyShare.privKey;
				ElGamalParametersC params = (ElGamalParametersC) priv.getParams();
				CivitasBigInteger ai = mc.a.modPow(priv.x, params.p);
				return new ElGamalDecryptionShareC(ai, ElGamalProofDiscLogEqualityC
						.constructProof(params, mc.a, params.g, priv.x));
			} catch (RuntimeException e) {
				if (DEBUG)
					e.printStackTrace(System.err);
			}
		}
		return null;
	}

	@Override
	public ElGamalDecryptionShare decryptionShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalDecryptionShareC.fromXML(r);
	}

	@Override
	public ElGamalReencryptFactor elGamalReencryptFactorFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalReencryptFactorC.fromXML(r);
	}

	@Override
	public PublicKeyCiphertext publicKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return PublicKeyCiphertextC.fromXML(r);
	}

	@Override
	public SharedKeyCiphertext sharedKeyCiphertextFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return SharedKeyCiphertextC.fromXML(r);
	}

	@Override
	public SharedKey sharedKeyFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return SharedKeyC.fromXML(r);
	}

	@Override
	public SharedKey sharedKeyFromWire(Reader r)
			throws IllegalArgumentException, IOException {
		if (r instanceof BufferedReader) {
			return SharedKeyC.fromWire((BufferedReader) r);
		} else {
			return SharedKeyC.fromWire(new BufferedReader(r));
		}
	}

	@Override
	public ElGamalProofDVR elGamalProofDVRFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalProofDVRC.fromXML(r);
	}

	@Override
	public ElGamalPrivateKey egPrivKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPrivateKeyFromXML(
				new BufferedReader(new FileReader(keyFile)));
	}

	@Override
	public ElGamalPublicKey egPubKeyFromFile(String keyFile)
			throws IllegalArgumentException, FileNotFoundException, IOException {
		return elGamalPublicKeyFromXML(new BufferedReader(new FileReader(keyFile)));
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

	@Override
	public ElGamalKeyShare elGamalKeyShareFromXML(Reader r)
			throws IllegalArgumentException, IOException {
		return ElGamalKeyShareC.fromXML(r);
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
			numPublicKeySign++;
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
			numPublicKeyVerifySig++;
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
		if (DEBUG)
			Thread.dumpStack();
		return null;
	}

	@Override
	public byte[] freshNonce(int bitlength) {
		int bytelength = bitlength / 8;
		if (bitlength % 8 != 0)
			bytelength++;
		byte[] bs = new byte[bytelength];
		CryptoAlgs.rng().nextBytes(bs);
		return bs;
	}

	@Override
	public MessageDigest messageDigest() {
		try {
			if (MESSAGE_DIGEST_PROVIDER == null) {
				return new MessageDigestC(
						java.security.MessageDigest.getInstance(MESSAGE_DIGEST_ALG));
			} else {
				return new MessageDigestC(java.security.MessageDigest
						.getInstance(MESSAGE_DIGEST_ALG, MESSAGE_DIGEST_PROVIDER));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("No provider " + MESSAGE_DIGEST_PROVIDER);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create message digest", e);
		}
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
		if (n <= 0)
			return 0;
		return CryptoAlgs.rng().nextInt(n);
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
			return ElGamalProofDVRC.constructProof((ElGamalCiphertextC) e,
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
			return ElGamalProofDVRC.fakeProof((ElGamalCiphertextC) e,
					(ElGamalCiphertextC) ePrime, (ElGamalPublicKeyC) k,
					(ElGamalPublicKeyC) verifierKey,
					(ElGamalPrivateKeyC) verifierPrivKey);
		} catch (ClassCastException ex) {
			return null;
		}
	}

	@Override
	public PublicKeyCiphertext publicKeyEncrypt(PublicKey key, PublicKeyMsg msg) {
		numPublicKeyEncs++;
		PublicKeyC keyc = (PublicKeyC) key;
		PublicKeyMsgC msgc = (PublicKeyMsgC) msg;
		byte[] encrypted = jseCrypt(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER,
				keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new PublicKeyCiphertextC(encrypted);
	}

	@Override
	public PublicKeyMsg publicKeyDecrypt(PrivateKey key,
			PublicKeyCiphertext ciphertext) throws CryptoException {
		numPublicKeyDecs++;
		PrivateKeyC keyc = (PrivateKeyC) key;
		PublicKeyCiphertextC ciphertextc = (PublicKeyCiphertextC) ciphertext;
		byte[] plaintext = jseCrypt(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER,
				keyc.k, Cipher.DECRYPT_MODE, ciphertextc.toBytes());
		return new PublicKeyMsgC(plaintext);
	}

	@Override
	public SharedKey generateSharedKey(int keyLength) {
		SecretKey k = sharedKeyGenerator(keyLength).generateKey();
		return new SharedKeyC(k, "sharedKey-civitas");
	}

	@Override
	public SharedKeyCiphertext sharedKeyEncrypt(SharedKey key, SharedKeyMsg msg) {
		numSharedKeyEncs++;
		SharedKeyC keyc = (SharedKeyC) key;
		SharedKeyMsgC msgc = (SharedKeyMsgC) msg;
		byte[] encrypted = jseCrypt(SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER,
				keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new SharedKeyCiphertextC(encrypted);
	}

	@Override
	public SharedKeyMsg sharedKeyDecrypt(SharedKey key,
			SharedKeyCiphertext ciphertext) throws CryptoException {
		numSharedKeyDecs++;
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

	public byte[] sharedKeyToBytes(SecretKey k) {
		return k.getEncoded();
	}

	public SecretKey sharedKeyFromBytes(byte[] bs) {
		SecretKeySpec skeySpec = secretKeyAlgKeySpec(bs);
		if (sharedKeyFactory == null) {
			// no factory needed
			return skeySpec;
		}
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
		return bytesToBase64(freshNonce(bitlength));
	}

	@Override
	public String bytesToBase64(byte[] a) {
		return Base64.getEncoder().encodeToString(a);
	}

	@Override
	public String constBytesToBase64(byte[] a) {
		return Base64.getEncoder().encodeToString(a);
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
		return Base64.getEncoder().encodeToString(i.toByteArray());
	}

	public static CivitasBigInteger stringToBigInt(String s) {
		return new CivitasBigInteger(Base64.getDecoder().decode(s));
	}

	@Override
	public ProofVote constructProofVote(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamal1OfLReencryption encChoice,
			String context, ElGamalReencryptFactor encCapabilityFactor,
			ElGamalReencryptFactor encChoiceFactor) {
		try {
			return new ProofVoteC((ElGamalParametersC) params,
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
		return ProofVoteC.fromXML(r);
	}

	public static long numPublicKeyEncs() {
		return numPublicKeyEncs;
	}

	public static long numPublicKeyDecs() {
		return numPublicKeyDecs;
	}

	public static long numSharedKeyEncs() {
		return numSharedKeyEncs;
	}

	public static long numSharedKeyDecs() {
		return numSharedKeyDecs;
	}

	public static long numElGamalEncs() {
		return numElGamalEncs;
	}

	public static long numElGamalDecs() {
		return numElGamalDecs;
	}

	public static long numElGamalDecShare() {
		return numElGamalDecShare;
	}

	public static long numPublicKeySign() {
		return numPublicKeySign;
	}

	public static long numPublicKeyVerifySig() {
		return numPublicKeyVerifySig;
	}

	public static long numElGamalReencs() {
		return numElGamalReencs;
	}

	public static long numElGamalSignedEncs() {
		return numElGamalSignedEncs;
	}

	public static long numElGamalVerifies() {
		return numElGamalVerifies;
	}

}