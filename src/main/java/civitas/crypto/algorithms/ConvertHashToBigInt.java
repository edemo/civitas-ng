package civitas.crypto.algorithms;

import civitas.util.CivitasBigInteger;

public class ConvertHashToBigInt {

	public CivitasBigInteger apply(byte[] hash) {
		// Force the hash to be positive.
		CivitasBigInteger x = new CivitasBigInteger(1, hash);
		return x;
	}

}
