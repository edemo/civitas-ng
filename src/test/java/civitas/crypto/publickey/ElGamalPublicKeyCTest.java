package civitas.crypto.publickey;

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

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalPublicKeyCTest extends ConcreteTestBase
		implements ElGamalPublicKeyCTestData {

	ElGamalPublicKey elGamalPublicKeyC;

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;

	@Override
	@BeforeEach
	public void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		super.setUp();
		elGamalPublicKeyC = new ElGamalPublicKey(
				new CivitasBigInteger(Base64.getDecoder().decode(G_EXP_A_BASE64)),
				EL_GAMAL_PARAMETERS);
	}

	@Use
	ElGamalPublicKeyToXML elGamalPublicKeyToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_PUBLIC_KEY_XML,
				elGamalPublicKeyToXML.apply(elGamalPublicKeyC));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(elGamalPublicKeyC, elGamalPublicKeyFromXML
				.apply(new StringReader(EL_GAMAL_PUBLIC_KEY_XML)));
	}

	@Use
	GetElGamalPublicKeyName getElGamalPublicKeyName;

	@Test
	@DisplayName("name returns a name")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMALPUBLIC_KEY_NAME,
				getElGamalPublicKeyName.apply(elGamalPublicKeyC));
	}

	@Use
	ElGamalPublicKeyisAuthorized elGamalPublicKeyisAuthorized;

	@Test
	@DisplayName("isAuthorized checks if the proof is the private key for this")
	void test3() throws IllegalArgumentException, IOException {
		assertTrue(elGamalPublicKeyisAuthorized.apply(elGamalPublicKeyC,
				ELGAMAL_PRIVATE_KEY_E));
	}

	@Test
	@DisplayName("isAuthorized is false for a secret which is not a private key")
	void test4() throws IllegalArgumentException, IOException {
		assertFalse(
				elGamalPublicKeyisAuthorized.apply(elGamalPublicKeyC, BIGINT_A));
	}

	@Test
	@DisplayName("isAuthorized is false for bad secret")
	void test5() throws IllegalArgumentException, IOException {
		assertFalse(elGamalPublicKeyisAuthorized.apply(elGamalPublicKeyC,
				new ElGamalPrivateKey(BIGINT_B, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("isAuthorized throws NullPointerException for a private key with null")
	void test6() throws IllegalArgumentException, IOException {
		assertThrows(NullPointerException.class,
				() -> elGamalPublicKeyisAuthorized.apply(elGamalPublicKeyC,
						new ElGamalPrivateKey(null, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("not equals to other key with different y but same parameters "
			+ "FIXME original code did not test for y")
	void equalsTest2() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_PUBLIC_KEY_EPRIME
				.equals(new ElGamalPublicKey(BIGINT_B, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("not equals to anything not ElGamalPublicKeyC")
	void equalsTest3() throws IllegalArgumentException, IOException {
		assertFalse(
				EL_GAMAL_PUBLIC_KEY_EPRIME.equals(mock(ElGamalPublicKey.class)));
	}

}
