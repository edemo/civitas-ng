package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.CryptoError;
import civitas.util.CivitasBigInteger;

public class CheckGroupTest extends TestBase
		implements ElGamalParametersTestData {

	@InjectMocks
	CheckGroup checkGroup;

	@Test
	@DisplayName("checkGroup does nothing if the parameters are a group")
	void test() {
		assertDoesNotThrow(() -> checkGroup.apply(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("checkGroup throws CryptoError if parameters are not a group")
	void test_1() throws Exception {
		assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParameters(BIGINT_A, BIGINT_B, BIGINT_C)));
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q does not divide p-1")
	void checkGroupTest() {
		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParameters(BIGINT_P, BIGINT_P, GENERATOR_OTHER)));
		assertEquals("q does not divide p-1", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if p is not prime")
	void checkGroupTest1() {
		CivitasBigInteger p;
		p = BIGINT_Q.multiply(CivitasBigInteger.valueOf(2)).add(ONE);
		CryptoError t = assertThrows(CryptoError.class, () -> {
			checkGroup
					.apply(new ElGamalParameters(p, BIGINT_Q, GENERATOR_FOR_UNPRIME_P));
		});
		assertEquals("p is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest5() {
		CryptoError t = assertThrows(CryptoError.class, () -> {
			checkGroup.apply(new ElGamalParameters(BIGINT_P, BIGINT_A, BIGINT_G));
		});
		assertEquals("q is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not generator")
	void checkGroupTest2() {
		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParameters(BIGINT_P, BIGINT_Q, BIGINT_A)));
		assertEquals("g is not order q", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest3() {
		CivitasBigInteger q = BIGINT_A;
		CivitasBigInteger p = q.multiply(TWO).add(ONE);

		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParameters(p, q, GENERATOR_FOR_UNPRIME_Q)));
		assertEquals("p is not prime", t.getMessage());
	}

}
