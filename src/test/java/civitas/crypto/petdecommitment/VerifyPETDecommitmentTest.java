package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.petcommitment.PETCommitmentCTestData;
import civitas.crypto.petcommitment.VerifyPETDecommitment;
import civitas.crypto.petshare.PETShareTestData;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyPETDecommitmentTest extends ConcreteTestBase
		implements PETDecommitmentCTestData, PETCommitmentCTestData,
		ElGamalCiphertextCTestData, PETShareTestData {

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

		ElGamalProofDiscLogEqualityC proof = (ElGamalProofDiscLogEqualityC) PET_DECOMMITMENT.proof;

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
	@DisplayName("the commitment must be of type PETCommitmentC")
	public void test1() {
		assertFalse(
				verifyPETDecommitment.apply(PET_DECOMMITMENT, mock(PETCommitment.class),
						EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));

	}

	@Test
	@DisplayName("if di of the decommitment is null, the verification fails")
	public void test2() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(null,
				PET_DECOMMITMENT_E, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if ei of the decommitment is null, the verification fails")
	public void test3() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				null, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g1 of the proof != ciphertext1.a/ciphertext2.a the verification fails")
	public void test4() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEqualityC(BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_B,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R));

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("if g2 of the proof != ciphertext1.b/ciphertext2.b the verification fails")
	public void test5() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEqualityC(
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1, BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
						EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W,
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
		ElGamalProofDiscLogEqualityC proof = new ElGamalProofDiscLogEqualityC(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G1,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_G2, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_V,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_W, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_R);
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E, proof);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

}
