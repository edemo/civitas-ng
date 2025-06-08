package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class PublicKeyFromFileTest extends TestBase implements PublicKeyTestData {

	@Tested
	PublicKeyFromFile publicKeyFromFile;

	@DisplayName("reads the public key from the named file")
	@Test
	void test()
			throws FileNotFoundException, IOException, InvalidKeySpecException {
		assertEquals(PUBLIC_KEY, publicKeyFromFile.apply("public_key.xml"));
	}

}
