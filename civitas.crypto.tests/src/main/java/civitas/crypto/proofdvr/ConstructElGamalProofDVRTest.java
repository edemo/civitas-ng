package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

public class ConstructElGamalProofDVRTest extends RandomAwareTestBase
		implements ElGamalProofDVRTestData {

	@InjectMocks
	ConstructElGamalProofDVR constructElGamalProofDVR;

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
	void test1() throws IOException {

		ElGamalProofDVR proof = constructElGamalProofDVR.apply(CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME,
				ZETA);
		assertEquals(EL_GAMAL_PROOF_DVR, proof);
	}

	@Test
	@DisplayName("the version where factors are given computes zeta")
	void test1_1() throws IOException {
		ElGamalProofDVR proof = constructElGamalProofDVR.apply(
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, ELGAMAL_REENCRYPT_FACTOR_E,
				ELGAMAL_REENCRYPT_FACTOR_EPRIME);
		assertEquals(EL_GAMAL_PROOF_DVR, proof);
	}

}
