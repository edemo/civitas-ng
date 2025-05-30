package civitas.crypto.signature;

import civitas.util.DI;

public class VerifyElGamalSignatureStub {

	public static VerifyElGamalSignature stub() {
		VerifyElGamalSignature stub = new VerifyElGamalSignature();
		DI.fill(stub);
		return stub;
	}

}
