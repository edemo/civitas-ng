package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.SchnorrPrime;
import civitas.crypto.algorithms.FindGenerator;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public class ElGamalParametersCTest extends ConcreteTestBase {

	FindGenerator findGenerator = DI.get(FindGenerator.class);

	@Test
	@DisplayName("storing constructor and toXML works as expected")
	void test() {
		assertEquals(ELGAMAL_PARAMETERS_XML,
				new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_G).toXML());
	}

	@Test
	@DisplayName("storing constructor throws CryptoError if parameters are not a group")
	void test_1() throws Exception {
		assertThrows(CryptoError.class,
				() -> new ElGamalParametersC(BIGINT_A, BIGINT_B, BIGINT_C));
	}

	@Test
	@DisplayName("equals and fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_G),
				ElGamalParametersC.fromXML(new StringReader(ELGAMAL_PARAMETERS_XML)));
	}

	@Test
	@DisplayName("fromXML throws CryptoError if group check fails")
	void test4() throws Exception {
		assertThrows(CryptoError.class, () -> ElGamalParametersC
				.fromXML(new StringReader(ELGAMAL_PARAMETERS_NOGROUP_XML)));
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

	@Test
	@DisplayName("checkGroup throws CryptoException if q does not divide p-1")
	void checkGroupTest() {
		CryptoError t = assertThrows(CryptoError.class,
				() -> new ElGamalParametersC(BIGINT_P, BIGINT_P, GENERATOR_OTHER));
		assertEquals("q does not divide p-1", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if p is not prime")
	void checkGroupTest1() {
		CivitasBigInteger p;
		p = BIGINT_Q.multiply(CivitasBigInteger.valueOf(2))
				.add(CivitasBigInteger.ONE);
		CivitasBigInteger g = findGenerator.apply(new SchnorrPrime(p, BIGINT_Q));
		CryptoError t = assertThrows(CryptoError.class, () -> {
			new ElGamalParametersC(p, BIGINT_Q, g);
		});
		assertEquals("p is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest5() {
		CryptoError t = assertThrows(CryptoError.class, () -> {
			new ElGamalParametersC(BIGINT_P, BIGINT_A, BIGINT_G);
		});
		assertEquals("q is not prime", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not generator")
	void checkGroupTest2() {
		CryptoError t = assertThrows(CryptoError.class,
				() -> new ElGamalParametersC(BIGINT_P, BIGINT_Q, BIGINT_A));
		assertEquals("g is not order q", t.getMessage());
	}

	@Test
	@DisplayName("checkGroup throws CryptoException if q is not prime")
	void checkGroupTest3() {
		CivitasBigInteger q = BIGINT_A;
		CivitasBigInteger p = q.multiply(CivitasBigInteger.TWO)
				.add(CivitasBigInteger.ONE);
		CivitasBigInteger g = findGenerator.apply(new SchnorrPrime(p, q));

		CryptoError t = assertThrows(CryptoError.class,
				() -> new ElGamalParametersC(p, q, g));
		assertEquals("p is not prime", t.getMessage());
	}

	@Test
	@DisplayName("hashCode is the xor of the hascode of the el gamal parameters")
	void hashCodeTest() {
		assertEquals(
				BIGINT_P.hashCode() ^ BIGINT_Q.hashCode() ^ BIGINT_G.hashCode(),
				EL_GAMAL_PARAMETERS.hashCode());
	}

}
