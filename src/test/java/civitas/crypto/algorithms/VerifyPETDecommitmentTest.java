package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.PETCommitment;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.crypto.concrete.PETCommitmentCTestData;
import civitas.crypto.concrete.PETDecommitmentC;
import civitas.crypto.concrete.PETDecommitmentCTestData;
import civitas.crypto.concrete.PETShareCTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyPETDecommitmentTest extends ConcreteTestBase
		implements PETDecommitmentCTestData, PETCommitmentCTestData,
		ElGamalCiphertextCTestData, PETShareCTestData {

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

		CivitasBigInteger d = EL_GAMAL_CIPHERTEXT_A.a
				.modDivide(EL_GAMAL_CIPHERTEXT_B.a, BIGINT_P);
		CivitasBigInteger e = EL_GAMAL_CIPHERTEXT_A.b
				.modDivide(EL_GAMAL_CIPHERTEXT_B.b, BIGINT_P);

		assertEquals(proof.g1, d);
		assertEquals(proof.g2, e);
		assertEquals(Util.fromBigInt(PET_COMMITMENT.hash), Util.fromBigInt(
				cryptoHash.apply(PET_DECOMMITMENT.di, PET_DECOMMITMENT.ei)));
		// exponent: BIGINT_C
		assertTrue(verifyPETDecommitment.apply(PET_DECOMMITMENT, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("the commitment must be of type PETCommitmentC")
	public void test1() {
		assertFalse(
				verifyPETDecommitment.apply(PET_DECOMMITMENT, mock(PETCommitment.class),
						EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));

	}

	@Test
	@DisplayName("if di of the decommitment is null, the verification fails")
	public void test2() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(null,
				PET_DECOMMITMENT_E, EL_GAMAL_DISC_LOG_EQUALITY_PROOF);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("if ei of the decommitment is null, the verification fails")
	public void test3() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				null, EL_GAMAL_DISC_LOG_EQUALITY_PROOF);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("if g1 of the proof != ciphertext1.a/ciphertext2.a the verification fails")
	public void test4() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEqualityC(BIGINT_A, EL_GAMAL_DISC_LOG_EQUALITY_E,
						EL_GAMAL_DISC_LOG_EQUALITY_A, EL_GAMAL_DISC_LOG_EQUALITY_V,
						EL_GAMAL_DISC_LOG_EQUALITY_W, EL_GAMAL_DISC_LOG_EQUALITY_B,
						EL_GAMAL_DISC_LOG_EQUALITY_C, EL_GAMAL_DISC_LOG_EQUALITY_R));

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("if g2 of the proof != ciphertext1.b/ciphertext2.b the verification fails")
	public void test5() {
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E,
				new ElGamalProofDiscLogEqualityC(EL_GAMAL_DISC_LOG_EQUALITY_D, BIGINT_A,
						EL_GAMAL_DISC_LOG_EQUALITY_A, EL_GAMAL_DISC_LOG_EQUALITY_V,
						EL_GAMAL_DISC_LOG_EQUALITY_W, EL_GAMAL_DISC_LOG_EQUALITY_B,
						EL_GAMAL_DISC_LOG_EQUALITY_C, EL_GAMAL_DISC_LOG_EQUALITY_R));

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("if the hash in the commitment != hash(di,ei) the verification fails")
	public void test6() {

		assertFalse(
				verifyPETDecommitment.apply(PET_DECOMMITMENT, PET_COMMITMENT_BAD_HASH,
						EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

	@Test
	@DisplayName("if the proof does not verify the verification fails")
	public void test7() {
		ElGamalProofDiscLogEqualityC proof = new ElGamalProofDiscLogEqualityC(
				EL_GAMAL_DISC_LOG_EQUALITY_D, EL_GAMAL_DISC_LOG_EQUALITY_E, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_V, EL_GAMAL_DISC_LOG_EQUALITY_W, BIGINT_A,
				EL_GAMAL_DISC_LOG_EQUALITY_C, EL_GAMAL_DISC_LOG_EQUALITY_R);
		assertFalse(proof.verify(EL_GAMAL_PARAMETERS));
		PETDecommitmentC petDecommitment = new PETDecommitmentC(PET_DECOMMITMENT_D,
				PET_DECOMMITMENT_E, proof);

		assertFalse(verifyPETDecommitment.apply(petDecommitment, PET_COMMITMENT,
				EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B));
	}

}
