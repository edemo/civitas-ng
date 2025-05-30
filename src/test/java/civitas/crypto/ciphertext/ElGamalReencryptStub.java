package civitas.crypto.ciphertext;

import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.util.DI;

public class ElGamalReencryptStub {

	public static ElGamalReencrypt stub() {
		ElGamalReencrypt a = new ElGamalReencrypt();
		DI.fill(a);
		return a;
	}
}
