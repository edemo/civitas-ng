package civitas.crypto.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateSchnorrPrime implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	CalculateNumberOfPrimeTests calculateNumberOfPrimeTests;
	@Autowired
	CryptoBase cryptoBase;

	public PrimePair apply(int qLength, int pLength) {
		CivitasBigInteger p, q;

		final int NUM_P_TESTS = calculateNumberOfPrimeTests.apply(pLength);
		CivitasBigInteger l = TWO.pow(pLength); // l = 2^pLength
		boolean done = false;
		do {
			q = cryptoBase.obtainProbablePrime(qLength);
			int nP = 0;
			do {
				nP++;
				p = cryptoBase.generateRandomElement(l);
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
