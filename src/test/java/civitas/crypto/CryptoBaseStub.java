package civitas.crypto;

import static org.mockito.Mockito.mock;

import civitas.common.TestUtil;
import civitas.crypto.messagedigest.MessageDigest;

public class CryptoBaseStub {

	public static CryptoBase stub() {
		CryptoBase mock = mock(CryptoBase.class);
		mock.messageDigest = new MessageDigest(TestUtil.getBaselineDigest());

		return mock;
	}

}
