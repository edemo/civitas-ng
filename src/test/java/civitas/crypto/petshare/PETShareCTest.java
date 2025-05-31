package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.petdecommitment.PETDecommitmentCTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class PETShareCTest extends TestBase
		implements PETDecommitmentCTestData, PETShareTestData {

	@Use
	CryptoHash hash;
	@Use
	PetShareFromXML petShareFromXML;
	@Use
	PETShareToXML pETShareCToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {
		assertEquals(PET_SHARE_XML, pETShareCToXML.apply(PET_SHARE_C));
	}

	@Test
	@DisplayName("toXML does nothing with a null PrintWriter")
	void test2() {
		assertDoesNotThrow(() -> pETShareCToXML.apply(PET_SHARE_C, null));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IOException {
		assertEquals(PET_SHARE_XML, pETShareCToXML
				.apply(petShareFromXML.apply(new StringReader(PET_SHARE_XML))));
	}

	@Test
	@DisplayName("ciphertext1 contains the first ciphertext")
	void test4() {
		assertEquals(CIPHERTEXT_E, PET_SHARE_C.ciphertext1);
	}

	@Test
	@DisplayName("ciphertext2 contains the second ciphertext")
	void test5() {
		assertEquals(CIPHERTEXT_EPRIME, PET_SHARE_C.ciphertext2);
	}

	@Test
	@DisplayName("exponent contains the exponent")
	void test5_1() {
		assertEquals(FACTOR_E, PET_SHARE_C.exponent);
	}

	@Use
	ConstructPETCommitment constructPETCommitment;

	@Test
	@DisplayName("commitment returns hash((c1.a/c2.a)^exponent, (c1.b/c2.b)^exponent) (mod p)")
	void test6() {
		ElGamalCiphertext c1 = PET_SHARE_C.ciphertext1;
		ElGamalCiphertext c2 = PET_SHARE_C.ciphertext2;
		CivitasBigInteger exponent = PET_SHARE_C.exponent;

		CivitasBigInteger hashe = hash.apply(
				c1.a.modDivide(c2.a, BIGINT_P).modPow(exponent, BIGINT_P),
				c1.b.modDivide(c2.b, BIGINT_P).modPow(exponent, BIGINT_P));

		assertEquals(hashe,
				constructPETCommitment.apply(PET_SHARE_C, EL_GAMAL_PARAMETERS).hash);
	}

}
