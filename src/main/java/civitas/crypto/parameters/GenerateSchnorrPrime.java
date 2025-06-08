package civitas.crypto.parameters;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.algorithms.GetRandomGenerator;
import civitas.util.CivitasBigInteger;
import civitas.util.ObtainProbablePrime;
import civitas.util.Use;

public class GenerateSchnorrPrime implements Constants {
	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	GetRandomGenerator getRandomGenerator;
	@Use
	CalculateNumberOfPrimeTests calculateNumberOfPrimeTests;
	@Use
	ObtainProbablePrime obtainProbablePrime;

	public PrimePair apply(int qLength, int pLength) {
		CivitasBigInteger p, q;

		final int NUM_P_TESTS = calculateNumberOfPrimeTests.apply(pLength);
		CivitasBigInteger l = TWO.pow(pLength); // l = 2^pLength
		boolean done = false;
		do {
			q = obtainProbablePrime.apply(qLength, CERTAINTY,
					getRandomGenerator.apply());
			int nP = 0;
			do {
				nP++;
				p = generateRandomElement.apply(l);
				p.add(l);
				CivitasBigInteger m = p.mod(q.multiply(TWO));
				p = p.subtract(m).add(ONE);
				if (p.bitLength() == pLength) {
					if (p.isProbablePrime(CERTAINTY)) {
						done = true;
					}
				}
			} while (!done && nP < NUM_P_TESTS);
		} while (!done);
		return new PrimePair(p, q);
	}

}
