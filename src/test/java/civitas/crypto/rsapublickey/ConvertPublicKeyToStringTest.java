package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class ConvertPublicKeyToStringTest extends TestBase
		implements PublicKeyTestData {

	@Autowired
	ConvertPublicKeyToString convertPublicKeyToString;
	@Autowired
	ConvertStringToPublicKey convertStringToPublicKey;

	@Test
	void test()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			IOException, UnrecoverableKeyException, InvalidKeySpecException {
		PublicKey pubKey = convertStringToPublicKey.apply(PUBLIC_KEY_BASE64);
		String publicK = convertPublicKeyToString.apply(pubKey);
		assertEquals(PUBLIC_KEY_BASE64, publicK);
	}

}
