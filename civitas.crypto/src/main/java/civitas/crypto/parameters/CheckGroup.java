package civitas.crypto.parameters;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;

public class CheckGroup implements Constants {

	public void apply(ElGamalParameters that) {
		if (!that.p.isProbablePrime(CERTAINTY)) {
			throw new CryptoError("p is not prime");
		}
		if (!that.q.isProbablePrime(CERTAINTY)) {
			throw new CryptoError("q is not prime");
		}
		if (!ZERO.equals(that.p.subtract(ONE).mod(that.q))) {
			throw new CryptoError("q does not divide p-1");
		}
		if (!ONE.equals(that.g.modPow(that.q, that.p))) {
			throw new CryptoError("g is not order q");
		}
	}
}
