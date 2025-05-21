package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

public class ElGamalProofDVRCTest extends ConcreteTestBase {

	ElGamalProofDVRC EL_GAMAL_PROOF_DVR = new ElGamalProofDVRC(
			EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_CIPHERTEXT_EPRIME, DVR_C, RANDOMS_1,
			RANDOMS_2, DVR_U);

	@Test
	@DisplayName("constructor and toXML works")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML, EL_GAMAL_PROOF_DVR.toXML());
	}

	@Test
	@DisplayName("constructproof works")
	void test1() throws IllegalArgumentException, IOException {
		ElGamalReencryptFactor er = REENCRYPT_FACTOR_RANDOMS_1;
		ElGamalReencryptFactor erPrime = ELGAMAL_REENCRYPT_FACTOR;
		CivitasBigInteger zeta = ((ElGamalReencryptFactorC) erPrime).r
				.modSubtract(((ElGamalReencryptFactorC) er).r, BIGINT_Q);
		TestUtil.setUpFakeRandom();
		ElGamalProofDVRC proof = ElGamalProofDVRC.constructProof(
				EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY,
				EL_GAMAL_PUBLIC_KEY2, zeta);
		TestUtil.tearDownFakeRandom();
		assertEquals(EL_GAMAL_PROOF_DVR_XML, proof.toXML());
	}

	@Test
	@DisplayName("fakeProof can create a fake proof which verifies with the private key of the verifier")
	void test1_1() throws IllegalArgumentException, IOException {
		TestUtil.setUpFakeRandom();
		ElGamalProofDVRC proof = ElGamalProofDVRC.fakeProof(EL_GAMAL_CIPHERTEXT_E,
				EL_GAMAL_CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY, EL_GAMAL_PUBLIC_KEY2,
				ELGAMAL_PRIVATE_KEY2);
		TestUtil.tearDownFakeRandom();
		assertTrue(proof.verify(EL_GAMAL_PUBLIC_KEY, EL_GAMAL_PUBLIC_KEY2));
	}

	@Test
	@DisplayName("verify works")
	void test2() {
		assertTrue(
				EL_GAMAL_PROOF_DVR.verify(EL_GAMAL_PUBLIC_KEY, EL_GAMAL_PUBLIC_KEY2));
	}

	@Test
	@DisplayName("getE works")
	void getETest() {
		assertEquals(EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_PROOF_DVR.getE());
	}

	@Test
	@DisplayName("getEprime works")
	void getEprimeTest() {
		assertEquals(EL_GAMAL_CIPHERTEXT_EPRIME, EL_GAMAL_PROOF_DVR.getEprime());
	}

	@Test
	@DisplayName("fromXML works")
	void fromXMLTest() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML, ((ElGamalProofDVRC) ElGamalProofDVRC
				.fromXML(new StringReader(EL_GAMAL_PROOF_DVR_XML))).toXML());
	}

}
