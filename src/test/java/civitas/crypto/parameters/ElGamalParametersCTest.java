package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoError;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalParametersCTest extends ConcreteTestBase
		implements ElGamalParametersCTestData {
	@Use
	ElGamalParametersFromXML elGamalParametersFromXML;
	@Use
	ElGamalParametersToXML elGamalParametersToXML;

	@Test
	@DisplayName("constructor and toXML works as expected\n"
			+ "NOTE: the constructor does not check the group. Use checkgroup for that")
	void test() {
		assertEquals(EL_GAMAL_PARAMETERS_XML, elGamalParametersToXML
				.apply(new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_G)));
	}

	@Test
	@DisplayName("checkGroup throws CryptoError if parameters are not a group")
	void test_1() throws Exception {
		assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParametersC(BIGINT_A, BIGINT_B, BIGINT_C)));
	}

	@Test
	@DisplayName("equals and fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_G),
				elGamalParametersFromXML
						.apply(new StringReader(EL_GAMAL_PARAMETERS_XML)));
	}

	@Test
	@DisplayName("fromXML throws CryptoError if group check fails")
	void test4() throws Exception {
		assertThrows(CryptoError.class, () -> elGamalParametersFromXML
				.apply(new StringReader(ELGAMAL_PARAMETERS_NOGROUP_XML)));
	}

	@Test
	@DisplayName("equals is false if other is not ElGamalParametersC")
	void test3() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_PARAMETERS.equals(mock(ElGamalParameters.class)));
	}

	@Test
	@DisplayName("equals is false if p differs")
	void test3_1() {
		assertFalse(EL_GAMAL_PARAMETERS.equals(EL_GAMAL_PARAMETERS_Q_SAME));
	}

	@Test
	@DisplayName("equals is false if g differs")
	void test3_2() {
		ElGamalParametersC gen = new ElGamalParametersC(BIGINT_P, BIGINT_Q,
				GENERATOR_OTHER);
		assertFalse(EL_GAMAL_PARAMETERS.equals(gen));
	}

	@Use
	CheckGroup checkGroup;

	@Test
	@DisplayName("checkGroup throws CryptoException if q does not divide p-1")
	void checkGroupTest() {
		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParametersC(BIGINT_P, BIGINT_P, GENERATOR_OTHER)));
		assertEquals("q does not divide p-1", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if p is not prime")
	void checkGroupTest1() {
		CivitasBigInteger p;
		p = BIGINT_Q.multiply(CivitasBigInteger.valueOf(2)).add(ONE);
		CryptoError t = assertThrows(CryptoError.class, () -> {
			checkGroup
					.apply(new ElGamalParametersC(p, BIGINT_Q, GENERATOR_FOR_UNPRIME_P));
		});
		assertEquals("p is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest5() {
		CryptoError t = assertThrows(CryptoError.class, () -> {
			checkGroup.apply(new ElGamalParametersC(BIGINT_P, BIGINT_A, BIGINT_G));
		});
		assertEquals("q is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not generator")
	void checkGroupTest2() {
		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_A)));
		assertEquals("g is not order q", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest3() {
		CivitasBigInteger q = BIGINT_A;
		CivitasBigInteger p = q.multiply(TWO).add(ONE);

		CryptoError t = assertThrows(CryptoError.class, () -> checkGroup
				.apply(new ElGamalParametersC(p, q, GENERATOR_FOR_UNPRIME_Q)));
		assertEquals("p is not prime", t.getMessage());
	}

}
