package civitas.crypto.algorithms;

import civitas.crypto.Constants;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateRandomElement {

	@Use
	GetRandomGenerator getRandomGenerator;

	public CivitasBigInteger apply(CivitasBigInteger n) {
		CivitasBigInteger r = null;
		do {
			r = new CivitasBigInteger(n.bitLength(), getRandomGenerator.apply());
		} while (r.equals(Constants.ZERO) || r.compareTo(n) >= 0);
		return r;
	}

}
