package civitas.crypto.algorithms;

import civitas.util.DI;

public class ElGamalReencryptStub {

	public static ElGamalReencrypt stub() {
		ElGamalReencrypt a = new ElGamalReencrypt();
		DI.fill(a);
		return a;
	}
}
