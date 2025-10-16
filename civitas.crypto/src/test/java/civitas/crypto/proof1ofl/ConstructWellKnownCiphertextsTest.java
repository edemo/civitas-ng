package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.ciphertextlist.ElGamalCiphertextListTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import io.github.magwas.testing.TestBase;

class ConstructWellKnownCiphertextsTest extends TestBase
		implements ElGamalCiphertextListTestData, ElGamalPublicKeyTestData {

	@InjectMocks
	ConstructWellKnownCiphertexts constructWellKnownCiphertexts;

	@Test
	@DisplayName(
			"""
			generates a ciphertext list using the public key and the count
			for each number from 1 to n:
			- encodes the number: n -> g^n mod p
			- encrypts it using the public key
			and returns the list of the results
			""")
	void test() throws CryptoException {

		assertEquals(CIPHERTEXTLIST_TWO_LONG, constructWellKnownCiphertexts.apply(EL_GAMAL_PUBLIC_KEY_E, 2));
		verify(constructWellKnownCiphertexts.encodeMessage).apply(1, EL_GAMAL_PARAMETERS);
		verify(constructWellKnownCiphertexts.encodeMessage).apply(2, EL_GAMAL_PARAMETERS);
		verify(constructWellKnownCiphertexts.elGamalEncrypt)
				.apply(EL_GAMAL_PUBLIC_KEY_E, ONE_ENCODED, ENCRYPT_FACTOR_ZERO);
		verify(constructWellKnownCiphertexts.elGamalEncrypt)
				.apply(EL_GAMAL_PUBLIC_KEY_E, TWO_ENCODED, ENCRYPT_FACTOR_ZERO);
	}

	@Test
	@DisplayName("if the length is 1, a single ciphertext is returned as a list")
	void test1() throws CryptoException {
		assertEquals(CIPHERTEXTLIST_ONE_LONG, constructWellKnownCiphertexts.apply(EL_GAMAL_PUBLIC_KEY_E, 1));
	}

	@Test
	@DisplayName("if the length is less than 1, a CryptoException is thrown ")
	void test1_1() {
		assertThrows(CryptoException.class, () -> constructWellKnownCiphertexts.apply(EL_GAMAL_PUBLIC_KEY_E, 0));
	}

	@Test
	@DisplayName("if the key is null, a CryptoException is thrown")
	void test2() {
		assertThrows(CryptoException.class, () -> constructWellKnownCiphertexts.apply(null, 2));
	}
}
