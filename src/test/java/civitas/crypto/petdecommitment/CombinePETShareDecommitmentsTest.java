package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.petcommitment.CombinePETShareDecommitments;
import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;

public class CombinePETShareDecommitmentsTest extends TestBase
		implements PETDecommitmentTestData {

	@InjectMocks
	CombinePETShareDecommitments combinePETShareDecommitments;

	@Test
	@DisplayName("multiplies di and ei fields")
	void test() {
		ElGamalCiphertext actual = combinePETShareDecommitments
				.apply(PET_DECOMMITMENTS, EL_GAMAL_PARAMETERS);
		assertEquals(PET_DECOMMITMENT_D.modMultiply(BIGINT_A, BIGINT_P), actual.a);
		assertEquals(PET_DECOMMITMENT_E.modMultiply(BIGINT_B, BIGINT_P), actual.b);
	}

	@Test
	@DisplayName("does NOT check the proofs")
	void test2() {
		Field[] fields = CombinePETShareDecommitments.class.getFields();
		for (Field field : fields) {
			if (field.getType() == VerifyElGamalProofDiscLogEquality.class)
				fail();
		}
	}

	@Test
	@DisplayName("returns a ciphertext with a=ONE, b=ONE if decs is null")
	void test3() {
		assertEquals(new ElGamalCiphertext(ONE, ONE),
				combinePETShareDecommitments.apply(null, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("the three parameter version uses d to multiply a of the ciphertext")
	void test4() {
		ElGamalCiphertext actual = combinePETShareDecommitments
				.apply(PET_DECOMMITMENTS, EL_GAMAL_PARAMETERS, TWO);
		assertEquals(PET_DECOMMITMENT_D.modMultiply(BIGINT_A, BIGINT_P)
				.modMultiply(TWO, BIGINT_P), actual.a);
		assertEquals(PET_DECOMMITMENT_E.modMultiply(BIGINT_B, BIGINT_P), actual.b);
	}

}
