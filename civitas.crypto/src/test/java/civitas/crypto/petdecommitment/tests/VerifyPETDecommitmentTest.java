package civitas.crypto.petdecommitment.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.ciphertext.tests.ElGamalCiphertextTestData;
import civitas.crypto.petcommitment.tests.PETCommitmentTestData;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.petdecommitment.VerifyPETDecommitment;
import civitas.crypto.petshare.tests.PETShareTestData;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import io.github.magwas.konveyor.testing.TestBase;

class VerifyPETDecommitmentTest extends TestBase
		implements PETDecommitmentTestData, PETCommitmentTestData, ElGamalCiphertextTestData, PETShareTestData {

	@InjectMocks
	VerifyPETDecommitment verifyPETDecommitment;

	@Test
	@DisplayName(
			"""
			correct decommitment passes
				// d=ciphertext1.a/ciphertext2.a
				// e=ciphertext1.b/ciphertext2.b
				// d =? proof.g1
				// e =? proof.g2
				// c.hash =? hash(di,ei)
				// verify(proof)
			""")
	void test() {

		ElGamalProofDiscLogEquality proof = PET_DECOMMITMENT.proof();

		CivitasBigInteger d = CIPHERTEXT_E.a.modDivide(CIPHERTEXT_EPRIME.a, BIGINT_P);
		CivitasBigInteger e = CIPHERTEXT_E.b.modDivide(CIPHERTEXT_EPRIME.b, BIGINT_P);

		assertEquals(proof.g1(), d);
		assertEquals(proof.g2(), e);
		assertTrue(verifyPETDecommitment.apply(
				PET_DECOMMITMENT, PET_COMMITMENT, EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g1 of the proof != ciphertext1.a/ciphertext2.a the verification fails")
	void test4() {
		PETDecommitment petDecommitment = new PETDecommitment(
				PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEquality(
						BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_B,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R));

		assertFalse(verifyPETDecommitment.apply(
				petDecommitment, PET_COMMITMENT, EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g2 of the proof != ciphertext1.b/ciphertext2.b the verification fails")
	void test5() {
		PETDecommitment petDecommitment = new PETDecommitment(
				PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEquality(
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1,
						BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_B,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R));

		assertFalse(verifyPETDecommitment.apply(
				petDecommitment, PET_COMMITMENT, EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if the hash in the commitment != hash(di,ei) the verification fails")
	void test6() {

		assertFalse(verifyPETDecommitment.apply(
				PET_DECOMMITMENT, PET_COMMITMENT_BAD_HASH, EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if the proof does not verify the verification fails")
	void test7() {
		ElGamalProofDiscLogEquality proof = new ElGamalProofDiscLogEquality(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
				BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
				BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R);
		PETDecommitment petDecommitment = new PETDecommitment(PET_DECOMMITMENT_D, PET_DECOMMITMENT_E, proof);

		assertFalse(verifyPETDecommitment.apply(
				petDecommitment, PET_COMMITMENT, EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}
}
