package civitas.crypto.parameters;

import java.util.Random;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.GetRandomGenerator;
import civitas.util.CivitasBigInteger;
import civitas.util.ObtainProbablePrime;
import civitas.util.Use;

public class GenerateSafePrime implements Constants {

	@Use
	GetRandomGenerator getRandomGenerator;
	@Use
	ObtainProbablePrime obtainProbablePrime;

	public PrimePair apply(int length) {
		CivitasBigInteger possibleP, possibleQ;
		do {
			Random randomGenerator = getRandomGenerator.apply();
			possibleQ = obtainProbablePrime.apply(length, CERTAINTY,
					randomGenerator);
			possibleP = possibleQ.multiply(TWO).add(ONE);
		} while (!possibleP.isProbablePrime(CERTAINTY));
		return new PrimePair(possibleP, possibleQ);
	}

}
