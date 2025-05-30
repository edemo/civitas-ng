package civitas.crypto.rsapublickey;

import civitas.common.TestUtil;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;

public interface PublicKeyTestData extends PrivateKeyTestData {
	public static final String PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA40oZkaWQnrXJHUjQ4kbCusUI+7Wmm6ljrqf2q5lLZLoAyjpoc8GpHnortB3iNJIUcibuU2qXLVuYXMXxCeR43k+zypHGY4gQtesgGtLr0VX8kY0pwKAWHoJwWciVqOT00VWeE5TBo3az2D8AT05AC4bZ38zYDCPGDPNUlm7diKY1cU2rF/NkK6XLByjfo63kWudjZicGxldv2JovLDNTbIAVPfPDrcMm4c54VhlPKj0cQHVIo1CYyVHU4UmF7+KKV+ZCKE6QounPtfjjL6L9VRaqG6KaMc/m+WxqxhaHdKR9GbmAQyFHWaBLAvoCEFHtQ20dvWLAhA4a4XhfIswiNwIDAQAB";

	public static final String PUBLIC_KEY2_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4QraOxcThCsATxuHzTSiVLDsJ++ddk7AAw/8qB6vjMT0/MKbBlVoJyg7innrc2BhH1PLZCGrinOCRDJHoFN4xm/k54JY/6oNEtXmg6xuaMMiaMjQdfTh8WfgU6p/oKhXp6Oo/PfW0zjJqyK9qOOiFWbQRXf2mqg+amDo/ol5K+2Tl+C7fy3hi8r78kUFDmgflnW9amYtPt5mT+wz95hJ994tiQfoVWVD0i5HksJoxW2t0QL3lqCfDcrRTLTGQ/EbuXJbinJDaiA0NunmYYR/lEVOr613PmGxmBsPPEfO1uQpZwhZbNd0SaJVNOzKGQwbs7xY4vUZIQ1tgub4+owdEQIDAQAB";

	public static final java.security.PublicKey PUBLIC_KEY = TestUtil
			.generatePublic(PUBLIC_KEY_BASE64);
	public static final java.security.PublicKey PUBLIC_KEY2 = TestUtil
			.generatePublic(PUBLIC_KEY2_BASE64);

	public static final String PUBLIC_KEY_NAME = "bob";
	public static final String PUBLIC_KEY_XML = "<publicKey><name>"
			+ PUBLIC_KEY_NAME + "</name><key>" + PUBLIC_KEY_BASE64
			+ "</key></publicKey>";

}
