package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.Util;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.petcommitment.PETCommitmentTestData;
import civitas.crypto.petshare.PETShareTestData;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyPETDecommitmentTest extends TestBase
		implements PETDecommitmentTestData, PETCommitmentTestData,
		ElGamalCiphertextTestData, PETShareTestData {

	@Tested
	VerifyPETDecommitment verifyPETDecommitment;
	@Use
	CryptoHash cryptoHash;

	@Test
	@DisplayName("correct decommitment passes "
			+ "		// d=ciphertext1.a/ciphertext2.a\n"
			+ "		// e=ciphertext1.b/ciphertext2.b\n"
			+ "		// d =? proof.g1                \n"
			+ "		// e =? proof.g2                \n"
			+ "		// c.hash =? hash(di,ei)        \n"
			+ "		// verify(proof)                \n")
	public void test() {

		ElGamalProofDiscLogEquality proof = PET_DECOMMITMENT.proof;

		CivitasBigInteger d = CIPHERTEXT_E.a.modDivide(CIPHERTEXT_EPRIME.a,
				BIGINT_P);
		CivitasBigInteger e = CIPHERTEXT_E.b.modDivide(CIPHERTEXT_EPRIME.b,
				BIGINT_P);

		assertEquals(proof.g1, d);
		assertEquals(proof.g2, e);
		assertEquals(PET_COMMITMENT_HASH_BASE64, Util.fromBigInt(
				cryptoHash.apply(PET_DECOMMITMENT.di, PET_DECOMMITMENT.ei)));
		// exponent: BIGINT_C
		assertTrue(verifyPETDecommitment.apply(PET_DECOMMITMENT, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g1 of the proof != ciphertext1.a/ciphertext2.a the verification fails")
	public void test4() {
		PETDecommitment petDecommitment = new PETDecommitment(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEquality(BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_B,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R));

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g2 of the proof != ciphertext1.b/ciphertext2.b the verification fails")
	public void test5() {
		PETDecommitment petDecommitment = new PETDecommitment(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEquality(
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1, BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_B,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R));

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if the hash in the commitment != hash(di,ei) the verification fails")
	public void test6() {

		assertFalse(
				verifyPETDecommitment.apply(PET_DECOMMITMENT, PET_COMMITMENT_BAD_HASH,
						EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if the proof does not verify the verification fails")
	public void test7() {
		ElGamalProofDiscLogEquality proof = new ElGamalProofDiscLogEquality(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R);
		PETDecommitment petDecommitment = new PETDecommitment(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E, proof);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

}
