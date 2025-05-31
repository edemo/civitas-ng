package civitas.crypto.parameters.encoder;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class SafePrimeDecode {
	public CivitasBigInteger apply(CivitasBigInteger i,
			ElGamalParameters elGamalParameters) throws CryptoException {
		if (i.compareTo(elGamalParameters.p) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		if (i.compareTo(elGamalParameters.q) > 0) {
			i = elGamalParameters.p.subtract(i); // i = -i
		}
		return i;
	}

}
