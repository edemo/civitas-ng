package civitas.crypto.petdecommitment.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.petdecommitment.ConstructPETDecommitment;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.petshare.tests.PETShareTestData;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

class ConstructPETDecommitmentTest extends TestBase implements PETDecommitmentTestData, PETShareTestData {

	@InjectMocks
	ConstructPETDecommitment constructPETDecommitment;

	@Test
	@DisplayName("decommitment returns a PETDecommitment "
			+ "d=c1.a/c2.a (mod p), " + "e=c1.b/c2.b (mod p), "
			+ "proof=ElGamalDiscLogEqualityProof(parameters,d,e,exponent), "
			+ "di = d^exponent (mod p), " + "ei = e^exponent (mod p), "
			+ "returns PETDecommitment(di,ei,proof)")
	void decommitmentTest() throws IllegalAccessException {
		CivitasBigInteger exponent = FACTOR_E;

		CivitasBigInteger di =
				CIPHERTEXT_E_A.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P).modPow(exponent, BIGINT_P);
		CivitasBigInteger ei =
				CIPHERTEXT_E_B.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P).modPow(exponent, BIGINT_P);

		PETDecommitment decommitment =
				constructPETDecommitment.apply(EL_GAMAL_PARAMETERS, exponent, CIPHERTEXT_E, CIPHERTEXT_EPRIME);
		assertEquals(di, decommitment.di());
		assertEquals(ei, decommitment.ei());

		verify(TestUtil.dependency(constructPETDecommitment, ConstructElGamalDiscLogEqualityProof.class))
				.apply(
						EL_GAMAL_PARAMETERS,
						CIPHERTEXT_E_A.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P),
						CIPHERTEXT_E_B.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P),
						FACTOR_E);
		assertEquals(PET_DECOMMITMENT, decommitment);
	}

	@Test
	@DisplayName("decommitment cannot be instantiated with null arguments")
	void decommitmentNullTest() {
		assertThrows(NullPointerException.class, () -> new PETDecommitment(null, null, null));
	}
}
