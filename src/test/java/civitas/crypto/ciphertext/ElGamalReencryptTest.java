package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;

public class ElGamalReencryptTest extends TestBase
		implements ElGamalPublicKeyTestData, ElGamalCiphertextTestData {

	@InjectMocks
	ElGamalReencrypt elGamalReencrypt;

	@Test
	@DisplayName("elGamalReencrypt works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1() throws Exception {
		assertEquals(CIPHERTEXT_E_REENCRYPTED,
				elGamalReencrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_E));

	}

	@Test
	@DisplayName("elGamalReencrypt with a factor works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1_1() throws Exception {

		assertEquals(CIPHERTEXT_E_REENCRYPTED,
				elGamalReencrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, CIPHERTEXT_E,
						new ElGamalReencryptFactor(RANDOMS_0)));

	}

}
