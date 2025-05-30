package civitas.crypto.parameters.encoder;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class SchnorrPrimeDecoder {
	public CivitasBigInteger apply(CivitasBigInteger m,
			ElGamalParametersC elGamalParameters) throws CryptoException {
		throw new CryptoException(
				"Decoding is not supported for Schnorr prime groups.");
	}

}
