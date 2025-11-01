package civitas.crypto.rsapublickey.tests;

import static org.mockito.Mockito.mock;

import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import civitas.crypto.rsaprivatekey.tests.PrivateKeyTestData;

public interface PublicKeyTestData extends PrivateKeyTestData {
	String PUBLIC_KEY_BASE64 =
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA40oZkaWQnrXJHUjQ4kbCusUI+7Wmm6ljrqf2q5lLZLoAyjpoc8GpHnortB3iNJIUcibuU2qXLVuYXMXxCeR43k+zypHGY4gQtesgGtLr0VX8kY0pwKAWHoJwWciVqOT00VWeE5TBo3az2D8AT05AC4bZ38zYDCPGDPNUlm7diKY1cU2rF/NkK6XLByjfo63kWudjZicGxldv2JovLDNTbIAVPfPDrcMm4c54VhlPKj0cQHVIo1CYyVHU4UmF7+KKV+ZCKE6QounPtfjjL6L9VRaqG6KaMc/m+WxqxhaHdKR9GbmAQyFHWaBLAvoCEFHtQ20dvWLAhA4a4XhfIswiNwIDAQAB";

	String PUBLIC_KEY2_BASE64 =
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4QraOxcThCsATxuHzTSiVLDsJ++ddk7AAw/8qB6vjMT0/MKbBlVoJyg7innrc2BhH1PLZCGrinOCRDJHoFN4xm/k54JY/6oNEtXmg6xuaMMiaMjQdfTh8WfgU6p/oKhXp6Oo/PfW0zjJqyK9qOOiFWbQRXf2mqg+amDo/ol5K+2Tl+C7fy3hi8r78kUFDmgflnW9amYtPt5mT+wz95hJ994tiQfoVWVD0i5HksJoxW2t0QL3lqCfDcrRTLTGQ/EbuXJbinJDaiA0NunmYYR/lEVOr613PmGxmBsPPEfO1uQpZwhZbNd0SaJVNOzKGQwbs7xY4vUZIQ1tgub4+owdEQIDAQAB";

	String PUBLIC_KEY_BAD_BASE64 = "aGVsbG9rYQ==";
	String PUBLIC_KEY_JS_UNAUTH_BASE64 = "PUBLIC_KEY_JS_UNAUTH_BASE64";
	PublicKey PUBLIC_KEY = mock(PublicKey.class);
	PublicKey PUBLIC_KEY_BAD = mock(PublicKey.class);
	PublicKey PUBLIC_KEY2 = mock(PublicKey.class);
	PublicKey PUBLIC_KEY_UNAUTH = mock(PublicKey.class);

	X509EncodedKeySpec KEYSPEC_PUBLIC =
			new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY_BASE64));

	X509EncodedKeySpec KEYSPEC_PUBLIC_BAD =
			new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY_BAD_BASE64));
}
