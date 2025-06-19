package civitas.crypto.parameters.encoder;

import org.springframework.stereotype.Service;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Service
public class SchnorrPrimeEncode {
	public CivitasBigInteger apply(CivitasBigInteger x,
			ElGamalParameters elGamalParameters) throws CryptoException {
		if (x.compareTo(elGamalParameters.q) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		return elGamalParameters.g.modPow(x, elGamalParameters.p);
	}

}