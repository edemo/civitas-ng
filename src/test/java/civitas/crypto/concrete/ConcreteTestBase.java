package civitas.crypto.concrete;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.BeforeEach;

import civitas.common.CiphertextList;
import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public class ConcreteTestBase implements ConcreteTestData {

	public MessageDigestC md;
	public java.security.MessageDigest baselineDigest;

	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_E;
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_EPRIME;

	public static final java.security.PrivateKey PRIVATE_KEY;
	public static final java.security.PublicKey PUBLIC_KEY;
	public static final java.security.PrivateKey PRIVATE_KEY2;
	public static final java.security.PublicKey PUBLIC_KEY2;

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_OTHER;
	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY;
	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY2;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE_JUST_BIGINT;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_SAFE;
	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE;
	public static final VoteCapabilityC VOTE_CAPABILITY;
	public static final VoteCapabilityC VOTE_CAPABILITY_JUST_BIGINT;

	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY;
	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY2;
	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY_OTHER;
	public static final ElGamalProofKnowDiscLogC ELGAMAL_PROOF_KNOWN_DISC_LOG;
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_C;

	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_A;
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_B;
	public static final ElGamalProofDiscLogEqualityC EL_GAMAL_PROOF_DISC_LOG_EQUALITY;

	public static final ElGamal1OfLReencryptionC EL_GAMAL_1_OF_L_REENCRYPTION;
	public static final CiphertextList CIPHERTEXT_LIST;

	static {
		try {

			EL_GAMAL_PARAMETERS = ElGamalParametersC
					.fromXML(new StringReader(ELGAMAL_PARAMETERS_XML));
			EL_GAMAL_PARAMETERS_SAFE = ElGamalParametersC
					.fromXML(new StringReader(EL_GAMAL_PARAMETERS_SAFE_XML));
			EL_GAMAL_PARAMETERS_OTHER = ElGamalParametersC
					.fromXML(new StringReader(ELGAMAL_PARAMETERS_OTHER_XML));
			ELGAMAL_PRIVATE_KEY = new ElGamalPrivateKeyC(BIGINT_A,
					EL_GAMAL_PARAMETERS);
			EL_GAMAL_PUBLIC_KEY = new ElGamalPublicKeyC(PUBLICIZED_BIGINT_A,
					EL_GAMAL_PARAMETERS);
			EL_GAMAL_PUBLIC_KEY2 = new ElGamalPublicKeyC(
					BIGINT_G.modPow(BIGINT_B, BIGINT_P), EL_GAMAL_PARAMETERS);
			ELGAMAL_PRIVATE_KEY2 = new ElGamalPrivateKeyC(BIGINT_B,
					EL_GAMAL_PARAMETERS);
			EL_GAMAL_PUBLIC_KEY_OTHER = new ElGamalPublicKeyC(
					BIGINT_G_OTHER.modPow(BIGINT_A, BIGINT_P), EL_GAMAL_PARAMETERS_OTHER);
			VOTE_CAPABILITY_SHARE = new VoteCapabilityShareC(BIGINT_A,
					EL_GAMAL_PARAMETERS);
			VOTE_CAPABILITY_SHARE_JUST_BIGINT = new VoteCapabilityShareC(BIGINT_A);
			VOTE_CAPABILITY = new VoteCapabilityC(BIGINT_A, EL_GAMAL_PARAMETERS);
			VOTE_CAPABILITY_JUST_BIGINT = new VoteCapabilityC(BIGINT_A);

			SOME_INT_ENCRYPTED_SAFE = EL_GAMAL_PARAMETERS_SAFE
					.encodePlaintext(SOME_INT_BIG);

			boolean doGenerate = false;
			if (doGenerate) {
				KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
				generator.initialize(2048);
				KeyPair pair = generator.generateKeyPair();
				PublicKey pub = pair.getPublic();
				PrivateKey priv = pair.getPrivate();
				System.out
						.println(Base64.getEncoder().encodeToString(pub.getEncoded()));
				System.out
						.println(Base64.getEncoder().encodeToString(priv.getEncoded()));

				SecretKeySpec skeySpec = new SecretKeySpec(BYTES, "AES");
				SecretKeyFactory shkf = SecretKeyFactory.getInstance("AES", "BC");
				SecretKey skey = shkf.generateSecret(skeySpec);
				System.out
						.println(Base64.getEncoder().encodeToString(skey.getEncoded()));
			}
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

			PRIVATE_KEY = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(PRIVATE_KEY_BASE64)));

			PUBLIC_KEY = keyFactory.generatePublic(new X509EncodedKeySpec(
					Base64.getDecoder().decode(PUBLIC_KEY_BASE64)));

			PRIVATE_KEY2 = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(PRIVATE_KEY2_BASE64)));

			PUBLIC_KEY2 = keyFactory.generatePublic(new X509EncodedKeySpec(
					Base64.getDecoder().decode(PUBLIC_KEY2_BASE64)));

			EL_GAMAL_1_OF_L_REENCRYPTION = (ElGamal1OfLReencryptionC) ElGamal1OfLReencryptionC
					.fromXML(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML));
			CIPHERTEXT_LIST = new CiphertextList(
					factory.constructWellKnownCiphertexts(EL_GAMAL_PUBLIC_KEY,
							NO_OF_WELL_KNOWN_CIPHERTEXTS));
			ELGAMAL_PROOF_KNOWN_DISC_LOG = ElGamalProofKnowDiscLogC
					.fromXML(new StringReader(ELGAMAL_PROOF_KNOWN_DISC_LOG_XML));

			EL_GAMAL_KEY_SHARE_C = new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY,
					ELGAMAL_PROOF_KNOWN_DISC_LOG);

			EL_GAMAL_PROOF_DISC_LOG_EQUALITY = (ElGamalProofDiscLogEqualityC) ElGamalProofDiscLogEqualityC
					.fromXML(new StringReader(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML));

			EL_GAMAL_CIPHERTEXT_A = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_A_XML));
			EL_GAMAL_CIPHERTEXT_B = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_B_XML));
			EL_GAMAL_CIPHERTEXT_E = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_E_XML));
			EL_GAMAL_CIPHERTEXT_EPRIME = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_EPRIME_XML));

		} catch (Exception e) {
			throw new Error(e);
		}
	}

	@BeforeEach
	void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		DI.stubUp(this);

		java.security.MessageDigest messageDigest = java.security.MessageDigest
				.getInstance("SHA-256");
		baselineDigest = java.security.MessageDigest.getInstance("SHA-256");
		md = new MessageDigestC(messageDigest);

	}

}