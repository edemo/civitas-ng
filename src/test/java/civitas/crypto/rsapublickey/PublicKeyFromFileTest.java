package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class PublicKeyFromFileTest extends TestBase implements PublicKeyTestData {

	@InjectMocks
	PublicKeyFromFile publicKeyFromFile;

	@DisplayName("reads the public key from the named file")
	@Test
	void test()
			throws FileNotFoundException, IOException, InvalidKeySpecException {
		assertEquals(PUBLIC_KEY_ON_WIRE, publicKeyFromFile.apply(PUBLIC_KEY_FILE));
	}

}
