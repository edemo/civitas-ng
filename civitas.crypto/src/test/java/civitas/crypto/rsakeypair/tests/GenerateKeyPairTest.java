package civitas.crypto.rsakeypair.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.rsakeypair.GenerateKeyPair;
import io.github.magwas.konveyor.testing.TestBase;

class GenerateKeyPairTest extends TestBase implements KeyPairTestData {
	@InjectMocks
	GenerateKeyPair generateKeyPair;

	@Test
	@DisplayName("generates a new public key pair")
	void test() {
		assertEquals(KEYPAIR, generateKeyPair.apply(KEYSIZE));
	}
}
