package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.algorithms.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public class PETDecommitmentCTest extends ConcreteTestBase implements
		PETDecommitmentCTestData, ConcreteTestData, ElGamalParametersCTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		CivitasBigInteger d = TestUtil.asBigint(EL_GAMAL_DISC_LOG_EQUALITY_D_BASE64);
		CivitasBigInteger e = TestUtil.asBigint(EL_GAMAL_DISC_LOG_EQUALITY_E_BASE64);
		CivitasBigInteger zi = TestUtil.asBigint(EL_GAMAL_DISC_LOG_EQUALITY_ZI_BASE64);
		TestUtil.setUpFakeRandom();
		ElGamalProofDiscLogEqualityC proof = DI
				.get(ConstructElGamalDiscLogEqualityProof.class)
				.apply(EL_GAMAL_PARAMETERS, d, e, zi);
		TestUtil.tearDownFakeRandom();
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_PROOF_XML, proof.toXML());
		assertEquals(PET_DECOMMITMENT_XML,
				new PETDecommitmentC(PET_DECOMMITMENT_D, PET_DECOMMITMENT_E, proof)
						.toXML());
	}

}
