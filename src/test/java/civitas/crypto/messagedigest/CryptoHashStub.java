package civitas.crypto.messagedigest;

import civitas.crypto.CryptoBaseStub;

public class CryptoHashStub {

	public static CryptoHash stub() {
		CryptoHash mock = new CryptoHash();
		mock.cryptoBase = CryptoBaseStub.stub();
		return mock;
	}
}
