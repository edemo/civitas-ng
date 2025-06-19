package civitas.crypto.signature;

import civitas.crypto.messagedigest.CryptoHashStub;

public class VerifyElGamalSignatureStub {

	public static VerifyElGamalSignature stub() {
		VerifyElGamalSignature mock = new VerifyElGamalSignature();
		mock.cryptoHash = CryptoHashStub.stub();
		return mock;
	}

}
