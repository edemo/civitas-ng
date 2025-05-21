package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.util.CivitasBigInteger;

public class LegendreSymbol {

	public int apply(CivitasBigInteger a, CivitasBigInteger p,
			CivitasBigInteger q) {
		CivitasBigInteger j = a.modPow(q, p);
		if (j.equals(CivitasBigInteger.ONE)) {
			return 1;
		} else if (j.equals(p.subtract(CivitasBigInteger.ONE))) {
			return -1;
		} else if (j.equals(CivitasBigInteger.ZERO)) {
			return 0;
		} else {
			throw new CryptoError("Impossible Legendre symbol");
		}
	}

}
