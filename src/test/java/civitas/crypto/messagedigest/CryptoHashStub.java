package civitas.crypto.messagedigest;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.util.DI;

public class CryptoHashStub {

	public static CryptoHash stub() {
		CryptoHash cryptoHash = new CryptoHash();
		DI.stubFill(cryptoHash);
		return cryptoHash;
	}
}
