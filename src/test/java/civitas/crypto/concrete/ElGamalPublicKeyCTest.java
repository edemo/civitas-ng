package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.importing.ElGamalPublicKeyFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalPublicKeyCTest extends ConcreteTestBase
		implements ElGamalPublicKeyCTestData {

	ElGamalPublicKeyC elGamalPublicKeyC;

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;

	@Override
	@BeforeEach
	public void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		super.setUp();
		elGamalPublicKeyC = new ElGamalPublicKeyC(
				new CivitasBigInteger(
						Base64.getDecoder().decode(PUBLICIZED_BIGINT_A_BASE64)),
				EL_GAMAL_PARAMETERS);
	}

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_PUBLIC_KEY_XML, elGamalPublicKeyC.toXML());
	}

	@Test
	@DisplayName("constructor works with any parameter as null")
	void test0() {
		assertEquals(EL_GAMAL_PUBLIC_KEY_NULL_XML,
				new ElGamalPublicKeyC(null, null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(elGamalPublicKeyC, elGamalPublicKeyFromXML
				.apply(new StringReader(EL_GAMAL_PUBLIC_KEY_XML)));
	}

	@Test
	@DisplayName("name returns a name")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMALPUBLIC_KEY_NAME, elGamalPublicKeyC.name());
	}

	@Test
	@DisplayName("isAuthorized checks if the proof is the private key for this")
	void test3() throws IllegalArgumentException, IOException {
		assertTrue(elGamalPublicKeyC.isAuthorized(ELGAMAL_PRIVATE_KEY));
	}

	@Test
	@DisplayName("isAuthorized is false for a secret which is not a private key")
	void test4() throws IllegalArgumentException, IOException {
		assertFalse(elGamalPublicKeyC.isAuthorized(BIGINT_A));
	}

	@Test
	@DisplayName("isAuthorized is false for bad secret")
	void test5() throws IllegalArgumentException, IOException {
		assertFalse(elGamalPublicKeyC
				.isAuthorized(new ElGamalPrivateKeyC(BIGINT_B, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("isAuthorized throws NullPointerException for a private key with null")
	void test6() throws IllegalArgumentException, IOException {
		assertThrows(NullPointerException.class, () -> elGamalPublicKeyC
				.isAuthorized(new ElGamalPrivateKeyC(null, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("not equals to other key with different y but same parameters "
			+ "FIXME original code did not test for y")
	void equalsTest2() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_PUBLIC_KEY
				.equals(new ElGamalPublicKeyC(BIGINT_B, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("not equals to anything not ElGamalPublicKeyC")
	void equalsTest3() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_PUBLIC_KEY.equals(mock(ElGamalPublicKey.class)));
	}

}
