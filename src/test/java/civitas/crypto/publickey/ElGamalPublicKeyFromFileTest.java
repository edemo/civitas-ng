package civitas.crypto.publickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class ElGamalPublicKeyFromFileTest extends TestBase
		implements ElGamalPublicKeyTestData {

	@Tested
	ElGamalPublicKeyFromFile elGamalPublicKeyFromFile;

	@Test
	@DisplayName("reads an el gamal public key from the file")
	void test() throws FileNotFoundException, IOException {
		assertEquals(EL_GAMAL_PUBLIC_KEY_E,
				elGamalPublicKeyFromFile.apply("el_gamal_public_key.xml"));
	}

}
