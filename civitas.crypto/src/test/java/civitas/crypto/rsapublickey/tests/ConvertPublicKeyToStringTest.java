package civitas.crypto.rsapublickey.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.PublicKey;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.rsapublickey.ConvertPublicKeyToString;
import io.github.magwas.konveyor.testing.TestBase;

class ConvertPublicKeyToStringTest extends TestBase implements PublicKeyTestData {

	@InjectMocks
	ConvertPublicKeyToString convertPublicKeyToString;

	@Test
	void test() {
		PublicKey pub = mock(PublicKey.class);
		when(pub.getEncoded()).thenReturn(Base64.getDecoder().decode(PUBLIC_KEY_BASE64));
		assertEquals(PUBLIC_KEY_BASE64, convertPublicKeyToString.apply(pub));
	}
}
