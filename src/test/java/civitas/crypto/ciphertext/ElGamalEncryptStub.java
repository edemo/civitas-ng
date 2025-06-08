package civitas.crypto.ciphertext;

import civitas.util.DI;

class ElGamalEncryptStub {

	public static ElGamalEncrypt stub() {
		ElGamalEncrypt instance = new ElGamalEncrypt();
		DI.stubFill(instance);
		return instance;

	}
}
