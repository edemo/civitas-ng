package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.petshare.PETShareTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class ConstructPETDecommitmentTest extends ConcreteTestBase
		implements PETDecommitmentCTestData, PETShareTestData {

	@Tested
	ConstructPETDecommitment constructPETDecommitment;

	@Test
	@DisplayName("decommitment returns a PETDecommitment "
			+ "d=c1.a/c2.a (mod p), " + "e=c1.b/c2.b (mod p), "
			+ "proof=ElGamalDiscLogEqualityProof(parameters,d,e,exponent), "
			+ "di = d^exponent (mod p), " + "ei = e^exponent (mod p), "
			+ "returns PETDecommitment(di,ei,proof)")
	void decommitmentTest() {
		CivitasBigInteger exponent = FACTOR_E;

		CivitasBigInteger di = CIPHERTEXT_E_A
				.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P).modPow(exponent, BIGINT_P);
		CivitasBigInteger ei = CIPHERTEXT_E_B
				.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P).modPow(exponent, BIGINT_P);

		PETDecommitmentC decommitment = (PETDecommitmentC) constructPETDecommitment
				.apply(EL_GAMAL_PARAMETERS, exponent, CIPHERTEXT_E, CIPHERTEXT_EPRIME);
		assertEquals(di, decommitment.di);
		assertEquals(ei, decommitment.ei);

		verify(constructPETDecommitment.constructElGamalDiscLogEqualityProof).apply(
				EL_GAMAL_PARAMETERS,
				CIPHERTEXT_E_A.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P),
				CIPHERTEXT_E_B.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P), FACTOR_E);
		assertEquals(PET_DECOMMITMENT_XML, decommitment.toXML());

	}

}
