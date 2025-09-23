package civitas.crypto.parameters.encoder;

import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class SchnorrPrimeEncode {
	public CivitasBigInteger apply(final CivitasBigInteger x, final ElGamalParameters elGamalParameters)
			throws CryptoException {
		if (x.compareTo(elGamalParameters.q) > 0) {
			throw new CryptoException("Message is too large for parameters");
		}
		return elGamalParameters.g.modPow(x, elGamalParameters.p);
	}
}
