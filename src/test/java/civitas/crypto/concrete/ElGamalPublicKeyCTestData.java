package civitas.crypto.concrete;

import civitas.common.TestUtil;
import civitas.util.CivitasBigInteger;

public interface ElGamalPublicKeyCTestData extends ElGamalPrivateKeyCTestData {
	public static final String PUBLICIZED_SAFE_BIGINT_A_BASE64 = BIGINT_A_ENCRYPTED_SAFE_BASE64;
	public static final String PUBLICIZED_BIGINT_A_BASE64 = "bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM";

	public static final CivitasBigInteger PUBLICIZED_BIGINT_A = BIGINT_G
			.modPow(BIGINT_A, BIGINT_P);

	public static final String PUBLICIZED_BIGINT_B_BASE64 = "d9AUMsYNk5ZQ+ZbjfZz7UgyjyklNj+gwz/MjRyrlLjWVnK/ghtTfAv4qIisD407XCf8Whv+2Us9JcXDuh2ptfNIWJibe2RX6ivjBH/1VyaOylbJ4BGfLFcyNiPxrjAPzJ1LdlSHjkXII3VrmyD72hF4gnzrKExGmDyi9tffhcfZoZvTgxzeVdjvR2PKK/k4Ug0zqjyQyKJUnDP3RB1q+31/RB3S6E+LMyr9ToxQWRS7IymyzsQXV6smygtzZKE+PBU+gIJjxQ6ALt3/VImxazonOPhO9VCSoNfTVwAVvK5FdXvS6utK0GHTnHx0U/D/4W880NWwbB7DIxdvpYnjpK45MxVvoGelhUqMMPOjMM+7cn3o6QLATVB1QHbo7w7yH2P+Q0tcr9itruIjI2sajoZkEMG5n6DJunwqHVZx38BScxUlmKb8rKW8+AhOC3BXBe5biUDReBdRtpSkOFi5as1mWxsIH2EvGAG2bPUtIZuCn0phLG37M32XyhDiOEehm";
	public static final String PUBLICIZED_SAFE_LEGENDRE_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9P/qG4=";

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

	public static final String EL_GAMAL_PUBLIC_KEY_XML = "<elGamalPublicKey><params>"
			+ ELGAMAL_PARAMETERS_XML + "</params><y>" + PUBLICIZED_BIGINT_A_BASE64
			+ "</y></elGamalPublicKey>";

	public static final String EL_GAMAL_PUBLIC_KEY_NULL_XML = "<elGamalPublicKey>"
			+ "<params></params><y></y></elGamalPublicKey>";
	public static final String EL_GAMALPUBLIC_KEY_NAME = "ElGamalPublicKey-"
			+ PUBLICIZED_BIGINT_A_BASE64;
	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY = new ElGamalPublicKeyC(
			PUBLICIZED_BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY2 = new ElGamalPublicKeyC(
			BIGINT_G.modPow(BIGINT_B, BIGINT_P), EL_GAMAL_PARAMETERS);
	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY_OTHER = new ElGamalPublicKeyC(
			BIGINT_G_OTHER.modPow(BIGINT_A, BIGINT_P), EL_GAMAL_PARAMETERS_OTHER);

}
