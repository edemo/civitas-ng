package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ConcreteTestData;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.crypto.concrete.PETDecommitmentC;
import civitas.crypto.concrete.PETDecommitmentCTestData;
import civitas.crypto.concrete.PETShareCTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;
import civitas.util.Tested;

public class ConstructPETDecommitmentTest extends ConcreteTestBase
		implements PETDecommitmentCTestData, PETShareCTestData {

	@Tested
	ConstructPETDecommitment constructPETDecommitment;

	@Test
	@DisplayName("decommitment returns a PETDecommitment "
			+ "d=c1.a/c2.a (mod p), " + "e=c1.b/c2.b (mod p), "
			+ "proof=ElGamalDiscLogEqualityProof(parameters,d,e,exponent), "
			+ "di = d^exponent (mod p), " + "ei = e^exponent (mod p), "
			+ "returns PETDecommitment(di,ei,proof)")
	void decommitmentTest() {
		ElGamalCiphertextC c1 = PET_SHARE_C.ciphertext1;
		ElGamalCiphertextC c2 = PET_SHARE_C.ciphertext2;
		CivitasBigInteger exponent = PET_SHARE_C.exponent;

		CivitasBigInteger d = c1.a.modDivide(c2.a, BIGINT_P);
		CivitasBigInteger e = c1.b.modDivide(c2.b, BIGINT_P);

		CivitasBigInteger di = d.modPow(exponent, BIGINT_P);
		CivitasBigInteger ei = e.modPow(exponent, BIGINT_P);

		PETDecommitmentC decommitment = (PETDecommitmentC) constructPETDecommitment
				.apply(EL_GAMAL_PARAMETERS, exponent, c1, c2);
		assertEquals(PET_DECOMMITMENT_XML, decommitment.toXML());
		verify(constructPETDecommitment.constructElGamalDiscLogEqualityProof)
				.apply(EL_GAMAL_PARAMETERS, d, e, exponent);
		TestUtil.setUpFakeRandom();
		ElGamalProofDiscLogEqualityC realProof = DI
				.get(ConstructElGamalDiscLogEqualityProof.class)
				.apply(EL_GAMAL_PARAMETERS, d, e, exponent);
		TestUtil.tearDownFakeRandom();
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_PROOF_XML, realProof.toXML());
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_PROOF, decommitment.proof);
		assertEquals(di, decommitment.di);
		assertEquals(ei, decommitment.ei);

	}

	@Test
	@DisplayName("decommitment returns null if the parameters not of type ElGamalParametersC")
	void decommitmentTest1() {
		assertEquals(null,
				constructPETDecommitment.apply(mock(ElGamalParameters.class),
						PET_SHARE_C.exponent, PET_SHARE_C.ciphertext1,
						PET_SHARE_C.ciphertext2));
	}

}
