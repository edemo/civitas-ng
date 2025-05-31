package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructElGamalProofDVRCTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData {

	@Tested
	ConstructElGamalProofDVR constructElGamalProofDVR;
	@Use
	ConvertHashToBigInt convertHashToBigInt;
	@Use
	CryptoHash cryptoHash;
	@Use
	VerifyElGamalProofDVR verifyElGamalProofDVR;
	@Use
	ElGamalProofDVRToXML elGamalProofDVRToXML;

	//@formatter:off
	@Test
	@DisplayName("proofs that we know the unencrypted value of the message\n"
			+ "both ciphertexts e, eprime are encrypted with the key\n"
			+ "zeta is the difference of the factors used for encription:\n"
			+ "zeta = factor_eprime - factor_e such that\n"
			+ "   eprime.a = g^ factor_eprime\n"
			+ "   e.a = g^ factor_e\n"
			+ "d,w,r are randoms in Z_q\n"
			+ "a = g^d (mod p)\n"
			+ "b = key^d (mod p)\n"
			+ "s = g^w*verifierkey^r\n"
			+ "c = hash(e.a,e.b,eprime.a,eprime.b,a,b,s)\n"
			+ "u = d^(zeta*c+w)\n"
			+ "ElGamalProofDVRC(e, eprime, c, w, r, u)\n")
	//@formatter:on
	void test1() throws IllegalArgumentException, IOException {
		ElGamalProofDVR proof = constructElGamalProofDVR.apply(CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME,
				ZETA);
		assertEquals(EL_GAMAL_PROOF_DVR_XML, elGamalProofDVRToXML.apply(proof));
	}

	@Test
	@DisplayName("the version where factors are given computes zeta")
	void test1_1() throws IllegalArgumentException, IOException {
		ElGamalProofDVR proof = (ElGamalProofDVR) constructElGamalProofDVR.apply(
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, ELGAMAL_REENCRYPT_FACTOR_E,
				ELGAMAL_REENCRYPT_FACTOR_EPRIME);
		assertEquals(EL_GAMAL_PROOF_DVR_XML, elGamalProofDVRToXML.apply(proof));
	}

	@Test
	@Tag("testdata")
	@DisplayName("DVR_HASH_BASE64")
	void checkTestData() {

		CivitasBigInteger b = PUBKEY_E.modPow(RANDOMS_0, BIGINT_P);
		CivitasBigInteger s = BIGINT_G.modPow(RANDOMS_1, BIGINT_P)
				.modMultiply(PUBKEY_EPRIME.modPow(RANDOMS_2, BIGINT_P), BIGINT_P);
		List<CivitasBigInteger> proofenv = Arrays.asList(CIPHERTEXT_E.a,
				CIPHERTEXT_E.b, CIPHERTEXT_EPRIME.a, CIPHERTEXT_EPRIME.b,
				BIGINT_G.modPow(RANDOMS_0, BIGINT_P), b, s);
		CivitasBigInteger c = convertHashToBigInt.apply(cryptoHash.apply(proofenv))
				.mod(BIGINT_Q);
		assertEquals(DVR_HASH_BASE64, Util.fromBigInt(c));

	}
}
