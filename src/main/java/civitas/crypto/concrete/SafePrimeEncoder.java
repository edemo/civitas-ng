package civitas.crypto.concrete;

import civitas.crypto.CryptoException;
import civitas.crypto.Encoder;
import civitas.util.CivitasBigInteger;

class SafePrimeEncoder implements Encoder {
	/**
	 * 
	 */
	private final ElGamalParametersC elGamalParameters;

	/**
	 * @param elGamalParametersC
	 */
	SafePrimeEncoder(ElGamalParametersC elGamalParametersC) {
		elGamalParameters = elGamalParametersC;
	}

	@Override
	public CivitasBigInteger encodePlaintext(CivitasBigInteger x)
			throws CryptoException {
		CivitasBigInteger encoding = x;
		if (CryptoAlgs.legendreSymbol(encoding, elGamalParameters.p, elGamalParameters.q) == -1) {
			encoding = elGamalParameters.p.subtract(encoding); // encoding = -m
		}
		return encoding;
	}

	@Override
	public CivitasBigInteger decodeMessage(CivitasBigInteger i)
			throws CryptoException {
		if (i.compareTo(elGamalParameters.p) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		if (i.compareTo(elGamalParameters.q) > 0) {
			i = elGamalParameters.p.subtract(i); // i = -i
		}
		return i;
	}
}