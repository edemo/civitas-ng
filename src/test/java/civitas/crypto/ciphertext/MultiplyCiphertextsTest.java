package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.SignAndEncrypt;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class MultiplyCiphertextsTest extends TestBase
		implements MultiplyCiphertextsTestData {

	@Tested
	MultiplyCiphertexts multiplyCiphertexts;
	@Tested
	SignAndEncrypt signAndEncrypt;

	@Test
	@DisplayName("multiplies a matrix of ciphertexts\n"
			+ "by multiplying a's and b's in each row\n"
			+ "gives one ciphertext for each rows")
	void test() throws CryptoError, CryptoException {
		CiphertextList actual = multiplyCiphertexts.apply(CIPHERTEXT_MATRIX,
				EL_GAMAL_PARAMETERS);
		assertEquals(CivitasBigInteger.valueOf(2 * 11), actual.get(0).getA());
		assertEquals(CivitasBigInteger.valueOf(5 * 17), actual.get(1).getA());
		assertEquals(CivitasBigInteger.valueOf(3 * 13), actual.get(0).getB());
		assertEquals(CivitasBigInteger.valueOf(7 * 19), actual.get(1).getB());
	}

	@Test
	@DisplayName("returns null if the matrix is null")
	void test2() {
		assertEquals(null, multiplyCiphertexts.apply(null, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns null if encounters a null in the matrix")
	void test3() {
		assertEquals(null, multiplyCiphertexts
				.apply(new ElGamalSignedCiphertext[][] { null }, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns null if a ciphertext is not castable to ElGamalCiphertextC")
	void test4() {
		assertEquals(null,
				multiplyCiphertexts.apply(
						new ElGamalSignedCiphertext[][] {
								{ mock(ElGamalSignedCiphertext.class) } },
						EL_GAMAL_PARAMETERS));
	}

}
