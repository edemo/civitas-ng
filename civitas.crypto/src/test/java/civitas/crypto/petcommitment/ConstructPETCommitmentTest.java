package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.petshare.PETShareTestData;
import io.github.magwas.testing.TestBase;

class ConstructPETCommitmentTest extends TestBase implements PETShareTestData, PETCommitmentTestData {
	@InjectMocks
	ConstructPETCommitment constructPETCommitment;

	@Test
	@DisplayName("commitment returns hash((c1.a/c2.a)^exponent, (c1.b/c2.b)^exponent) (mod p)")
	void test6() {

		assertEquals(
				new PETCommitment(PET_COMMITMENT_HASH), constructPETCommitment.apply(PET_SHARE, EL_GAMAL_PARAMETERS));
	}
}
