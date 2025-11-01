package civitas.crypto.ciphertext.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.ciphertext.MultiplyCiphertexts;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.ciphertextlist.tests.ElGamalCiphertextListTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.SignAndEncrypt;
import civitas.util.CivitasBigIntegerFactory;

class MultiplyCiphertextsTest extends RandomAwareTestBase implements ElGamalCiphertextListTestData {

	@InjectMocks
	MultiplyCiphertexts multiplyCiphertexts;

	@InjectMocks
	SignAndEncrypt signAndEncrypt;

	@Test
	@DisplayName(
			"""
			multiplies a matrix of ciphertexts
			by multiplying a's and b's in each row
			gives one ciphertext for each rows
			""")
	void test() {
		CiphertextList actual = multiplyCiphertexts.apply(CIPHERTEXT_MATRIX, EL_GAMAL_PARAMETERS);
		assertEquals(CivitasBigIntegerFactory.obtain(2 * 11), actual.get(0).getA());
		assertEquals(CivitasBigIntegerFactory.obtain(5 * 17), actual.get(1).getA());
		assertEquals(CivitasBigIntegerFactory.obtain(3 * 13), actual.get(0).getB());
		assertEquals(CivitasBigIntegerFactory.obtain(7 * 19), actual.get(1).getB());
	}

	@Test
	@DisplayName("returns empty list if the matrix is null")
	void test2() {
		assertEquals(EMPTY_LIST, multiplyCiphertexts.apply(null, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns empty list if encounters a null in the matrix")
	void test3() {
		assertEquals(
				EMPTY_LIST, multiplyCiphertexts.apply(new ElGamalSignedCiphertext[][] {null}, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns empty list if a ciphertext is not castable to ElGamalCiphertextC")
	void test4() {
		assertEquals(
				EMPTY_LIST,
				multiplyCiphertexts.apply(
						new ElGamalSignedCiphertext[][] {{mock(ElGamalSignedCiphertext.class)}}, EL_GAMAL_PARAMETERS));
	}
}
