package civitas.crypto.rsaprivatekey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class PrivatekeyFromFileTest extends TestBase implements PrivateKeyTestData {

	@Tested
	PrivatekeyFromFile privatekeyFromFile;

	@Test
	@DisplayName("reads an RSA private key from file")
	void test()
			throws IllegalArgumentException, IOException, InvalidKeySpecException {
		assertEquals(PRIVATE_KEY, privatekeyFromFile.apply("rsa_private_key.xml"));
	}

}
