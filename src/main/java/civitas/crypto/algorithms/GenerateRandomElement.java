package civitas.crypto.algorithms;

import civitas.util.CivitasBigInteger;

public class GenerateRandomElement {

	public CivitasBigInteger apply(CivitasBigInteger n) {
		CivitasBigInteger r = null;
		do {
			r = new CivitasBigInteger(n.bitLength(), Constants.RANDOM_GENERATOR);
		} while (r.equals(CivitasBigInteger.ZERO) || r.compareTo(n) >= 0);
		return r;
	}

}
