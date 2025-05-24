package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class PETShareCTest extends ConcreteTestBase
		implements ConcreteTestData, PETDecommitmentCTestData, PETShareCTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {
		assertEquals(PET_SHARE_XML, PET_SHARE_C.toXML());
	}

	@Test
	@DisplayName("can be constructed with all nulls")
	void test1() {
		assertEquals(PET_SHARE_NULL_XML, new PETShareC(null, null, null).toXML());
	}

	@Test
	@DisplayName("toXML does nothing with a null PrintWriter")
	void test2() {
		assertDoesNotThrow(() -> PET_SHARE_C.toXML(null));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IOException {
		assertEquals(PET_SHARE_XML,
				PETShareC.fromXML(new StringReader(PET_SHARE_XML)).toXML());
	}

	@Test
	@DisplayName("ciphertext1 returns the first ciphertext")
	void test4() {
		assertEquals(EL_GAMAL_CIPHERTEXT_A, PET_SHARE_C.ciphertext1());
	}

	@Test
	@DisplayName("ciphertext2 returns the second ciphertext")
	void test5() {
		assertEquals(EL_GAMAL_CIPHERTEXT_B, PET_SHARE_C.ciphertext2());
	}

	@Test
	@DisplayName("exponent returns the exponent")
	void test5_1() {
		assertEquals(BIGINT_C, PET_SHARE_C.exponent());
	}

	@Test
	@DisplayName("commitment returns hash((c1.a/c2.a)^exponent, (c1.b/c2.b)^exponent) (mod p)")
	void test6() {
		ElGamalCiphertextC c1 = PET_SHARE_C.ciphertext1;
		ElGamalCiphertextC c2 = PET_SHARE_C.ciphertext2;
		CivitasBigInteger exponent = PET_SHARE_C.exponent;

		CivitasBigInteger hash = factory.hash(
				c1.a.modDivide(c2.a, BIGINT_P).modPow(exponent, BIGINT_P),
				c1.b.modDivide(c2.b, BIGINT_P).modPow(exponent, BIGINT_P));

		assertEquals(hash,
				((PETCommitmentC) PET_SHARE_C.commitment(EL_GAMAL_PARAMETERS)).hash);
	}

	@Test
	@DisplayName("commitment returns null if the type of parameters is not ElGamalParametersC "
			+ "FIXME: why not an exception thrown?")
	void test7() {

		assertEquals(null, (PET_SHARE_C.commitment(mock(ElGamalParameters.class))));
	}

}
