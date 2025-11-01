package civitas.crypto.proofdvr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.proofdvr.ConstructElGamalProofDVR;
import civitas.crypto.proofdvr.ElGamalProofDVR;

class ConstructElGamalProofDVRTest extends RandomAwareTestBase implements ElGamalProofDVRTestData {

	@InjectMocks
	ConstructElGamalProofDVR constructElGamalProofDVR;

	@Test
	@DisplayName(
			"""
			proofs that we know the unencrypted value of the message
			both ciphertexts e, eprime are encrypted with the key
			zeta is the difference of the factors used for encription:
			zeta = factor_eprime - factor_e such that
			eprime.a = g^ factor_eprime
			e.a = g^ factor_e
			d,w,r are randoms in Z_q
			a = g^d (mod p)
			b = key^d (mod p)
			s = g^w*verifierkey^r
			c = hash(e.a,e.b,eprime.a,eprime.b,a,b,s)
			u = d^(zeta*c+w)
			ElGamalProofDVRC(e, eprime, c, w, r, u
			""")
	void test1() {

		ElGamalProofDVR proof = constructElGamalProofDVR.apply(
				CIPHERTEXT_E, CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME, ZETA);
		assertEquals(EL_GAMAL_PROOF_DVR, proof);
	}

	@Test
	@DisplayName("the version where factors are given computes zeta")
	void test1_1() {
		ElGamalProofDVR proof = constructElGamalProofDVR.apply(
				EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_EPRIME,
				CIPHERTEXT_E,
				CIPHERTEXT_EPRIME,
				ELGAMAL_REENCRYPT_FACTOR_E,
				ELGAMAL_REENCRYPT_FACTOR_EPRIME);
		assertEquals(EL_GAMAL_PROOF_DVR, proof);
	}
}
