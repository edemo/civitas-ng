package civitas.crypto.parameters;

import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

public class BruteForceDecode {
	public int apply(ElGamalParameters that, CivitasBigInteger m, int L)
			throws CryptoException {
		// FIXME look up in table
		// now try brute force
		CivitasBigInteger x = that.g;
		for (int i = 1; i <= L; i++) {
			if (x.equals(m)) {
				return i;
			}
			x = x.modMultiply(that.g, that.p);
		}

		throw new CryptoException("Brute force decoding failed");
	}

}
