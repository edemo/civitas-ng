package civitas.crypto.algorithms;

import civitas.crypto.SchnorrPrime;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class FindGenerator implements Constants {
	@Use
	GenerateRandomElement generateRandomElement;

	public CivitasBigInteger apply(SchnorrPrime sp) {
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
