package civitas.crypto.concrete;

import civitas.crypto.CryptoException;
import civitas.crypto.Encoder;
import civitas.util.CivitasBigInteger;

public class SchnorrPrimeEncoder implements Encoder {
	/**
	 * 
	 */
	private final ElGamalParametersC elGamalParameters;

	/**
	 * @param elGamalParametersC
	 */
	SchnorrPrimeEncoder(ElGamalParametersC elGamalParametersC) {
		elGamalParameters = elGamalParametersC;
	}

	@Override
	public CivitasBigInteger encodePlaintext(CivitasBigInteger x)
			throws CryptoException {
		if (x.compareTo(elGamalParameters.q) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		return elGamalParameters.g.modPow(x, elGamalParameters.p);
	}

	@Override
	public CivitasBigInteger decodeMessage(CivitasBigInteger m)
			throws CryptoException {
		throw new CryptoException(
				"Decoding is not supported for Schnorr prime groups.");
	}
}