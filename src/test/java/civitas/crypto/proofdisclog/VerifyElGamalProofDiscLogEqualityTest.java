package civitas.crypto.proofdisclog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.Tested;

public class VerifyElGamalProofDiscLogEqualityTest extends TestBase
		implements ElGamalProofDiscLogEqualityCTestData {

	@Tested
	VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	@Test
	@DisplayName("verify is true for a correctly costructed proof "
			+ "To verify, check that g_1^r = av^c (mod p) and g_2^r = bw^c (mod p).")
	void test0_1() {
		assertTrue(verifyElGamalProofDiscLogEquality.apply(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof ")
	void test0_2() {
		assertFalse(verifyElGamalProofDiscLogEquality
				.apply(EL_GAMAL_DISC_LOG_EQUALITY_BAD_G1, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof (other branch)")
	void test0_3() throws IllegalArgumentException, IOException {
		assertFalse(verifyElGamalProofDiscLogEquality
				.apply(EL_GAMAL_DISC_LOG_EQUALITY_BAD_W, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false something is null")
	void test0_5() {
		assertFalse(verifyElGamalProofDiscLogEquality.apply(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				mock(ElGamalParameters.class)));
	}

	@Test
	@DisplayName("verify is false if an arithmetic exception happens")
	void test0_6() {
		assertFalse(verifyElGamalProofDiscLogEquality.apply(
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				EL_GAMAL_PARAMETERS_NEGATIVE_P));
	}

}
