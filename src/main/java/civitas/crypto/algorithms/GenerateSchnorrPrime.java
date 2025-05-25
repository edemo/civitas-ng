package civitas.crypto.algorithms;

import civitas.crypto.SchnorrPrime;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateSchnorrPrime implements Constants {
	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	GetRandomGenerator getRandomGenerator;

	public SchnorrPrime apply(int qLength, int pLength) {
		CivitasBigInteger p, q;

		final int NUM_P_TESTS = numPrimeTests(pLength);
		CivitasBigInteger l = TWO.pow(pLength); // l = 2^pLength
		boolean done = false;

		do {
			q = new CivitasBigInteger(qLength, CERTAINTY, getRandomGenerator.apply());

			int nP = 0;
			do {
				nP++;

				/* Make p a random integer of the correct length */
				p = generateRandomElement.apply(l); // 0 < p < l
				p.add(l); // l < p < 2l

				/* Round p-1 down to a multiple of 2q */
				CivitasBigInteger m = p.mod(q.multiply(TWO)); // m = p
																											// mod
																											// 2q
				p = p.subtract(m).add(ONE); // p = 1 (mod 2q)A random
																		// Schnorr prime
				// p=2kq+1

				/* Rounding may have made p too small */
				if (p.bitLength() == pLength) {
					if (p.isProbablePrime(CERTAINTY)) {
						done = true;
					}
				}
			} while (!done && nP < NUM_P_TESTS);

			/*
			 * If we get here, either we have a Schnorr prime pair, or we failed to
			 * find a prime p for the current q. In the latter case, pick a new q and
			 * start over.
			 */

		} while (!done);

		return new SchnorrPrime(p, q);
	}

	private static int numPrimeTests(int pLength) {
		int k = (int) Math.ceil(Math.log(pLength) / Math.log(2));
		return (int) Math.pow(2, k + 2);
	}

}
