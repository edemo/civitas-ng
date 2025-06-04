package civitas.crypto.parameters;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class FindGenerator implements Constants {
	@Use
	public GenerateRandomElement generateRandomElement;

	public CivitasBigInteger apply(PrimePair sp) {
		CivitasBigInteger g = null;
		boolean reject = false;
		CivitasBigInteger p = sp.p;
		CivitasBigInteger negONE = p.subtract(ONE);
		CivitasBigInteger twoK = p.subtract(ONE).divide(sp.q);
		do {
			g = generateRandomElement.apply(p);
			g = g.modPow(twoK, p);
			reject = g.equals(ONE) || g.equals(negONE);
		} while (reject);

		return g;
	}

}
