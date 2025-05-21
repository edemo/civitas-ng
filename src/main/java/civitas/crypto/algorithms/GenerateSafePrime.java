package civitas.crypto.algorithms;

import civitas.crypto.SchnorrPrime;
import civitas.util.CivitasBigInteger;

public class GenerateSafePrime {
	public SchnorrPrime apply(int length) {
		CivitasBigInteger possibleP, possibleQ;
		do {
			possibleQ = new CivitasBigInteger(length, Constants.CERTAINTY,
					Constants.RANDOM_GENERATOR);
			possibleP = possibleQ.multiply(CivitasBigInteger.TWO)
					.add(CivitasBigInteger.ONE); // p = 2q+1
		} while (!possibleP.isProbablePrime(Constants.CERTAINTY));
		return new SchnorrPrime(possibleP, possibleQ);
	}

}
