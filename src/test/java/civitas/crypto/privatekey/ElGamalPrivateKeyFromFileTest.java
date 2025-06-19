package civitas.crypto.privatekey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class ElGamalPrivateKeyFromFileTest extends TestBase
		implements ElGamalPrivateKeyTestData {

	@InjectMocks
	ElGamalPrivateKeyFromFile elGamalPrivateKeyFromFile;

	@DisplayName("reads a private key from file")
	@Test
	void test() throws FileNotFoundException, IOException {
		assertEquals(EL_GAMAL_PRIVATE_KEY_E,
				elGamalPrivateKeyFromFile.apply("el_gamal_private_key.xml"));
	}

}
