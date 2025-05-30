package civitas.crypto.parameters.encoder;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class SchnorrPrimeEncoder implements Encoder {
	public CivitasBigInteger apply(CivitasBigInteger x,
			ElGamalParametersC elGamalParameters) throws CryptoException {
		if (x.compareTo(elGamalParameters.q) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		return elGamalParameters.g.modPow(x, elGamalParameters.p);
	}

}