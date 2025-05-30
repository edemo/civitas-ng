package civitas.crypto.parameters;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class FindGenerator implements Constants {
	@Use
	public GenerateRandomElement generateRandomElement;

	public CivitasBigInteger apply(PrimePair sp) {
		// Implementation of step 3 of Algorithm 11.54 from Handbook of Applied
		// Cryptography
		CivitasBigInteger g = null;
		boolean reject = false;
		CivitasBigInteger p = sp.p;
		CivitasBigInteger negONE = p.subtract(ONE); // -1 mod p
		CivitasBigInteger twoK = p.subtract(ONE).divide(sp.q); // (p-1)/q
																														// =
																														// 2k
		do {
			g = generateRandomElement.apply(p);
			g = g.modPow(twoK, p);
			reject = g.equals(ONE) || g.equals(negONE);
		} while (reject);

		return g;
	}

}
