package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class MultiplyCiphertextsTest extends ConcreteTestBase
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
		ElGamalCiphertext[] actual = multiplyCiphertexts.apply(CIPHERTEXT_MATRIX,
				EL_GAMAL_PARAMETERS);
		assertEquals(CivitasBigInteger.valueOf(2 * 11),
				((ElGamalCiphertextC) actual[0]).a);
		assertEquals(CivitasBigInteger.valueOf(5 * 17),
				((ElGamalCiphertextC) actual[1]).a);
		assertEquals(CivitasBigInteger.valueOf(3 * 13),
				((ElGamalCiphertextC) actual[0]).b);
		assertEquals(CivitasBigInteger.valueOf(7 * 19),
				((ElGamalCiphertextC) actual[1]).b);
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
				.apply(new ElGamalSignedCiphertextC[][] { null }, EL_GAMAL_PARAMETERS));
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

	@Test
	@DisplayName("returns null if the parameters are not castable to ElGamalParametersC")
	void test5() {
		assertEquals(null,
				multiplyCiphertexts.apply(new ElGamalSignedCiphertext[][] {}, mock(ElGamalParameters.class)));
	}
}
