package civitas.crypto.algorithms;

import civitas.crypto.SchnorrPrime;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateSafePrime implements Constants {

	@Use
	GetRandomGenerator getRandomGenerator;

	public SchnorrPrime apply(int length) {
		CivitasBigInteger possibleP, possibleQ;
		do {
			possibleQ = new CivitasBigInteger(length, CERTAINTY,
					getRandomGenerator.apply());
			possibleP = possibleQ.multiply(TWO).add(ONE); // p = 2q+1
		} while (!possibleP.isProbablePrime(CERTAINTY));
		return new SchnorrPrime(possibleP, possibleQ);
	}

}
