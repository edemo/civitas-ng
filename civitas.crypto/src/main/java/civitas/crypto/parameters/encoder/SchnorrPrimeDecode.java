package civitas.crypto.parameters.encoder;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class SchnorrPrimeDecode {
	public CivitasBigInteger apply(CivitasBigInteger m,
			ElGamalParameters elGamalParameters) throws CryptoException {
		throw new CryptoException(
				"Decoding is not supported for Schnorr prime groups.");
	}

}
