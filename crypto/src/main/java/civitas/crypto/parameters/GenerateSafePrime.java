package civitas.crypto.parameters;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.algorithms.GetRandomGenerator;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateSafePrime implements Constants {

	@Autowired
	GetRandomGenerator getRandomGenerator;
	@Autowired
	CryptoBase cryptoBase;

	public PrimePair apply(int length) {
		CivitasBigInteger possibleP, possibleQ;
		do {
			Random randomGenerator = getRandomGenerator.apply();
			possibleQ = cryptoBase.obtainProbablePrime(length, CERTAINTY,
					randomGenerator);
			possibleP = possibleQ.multiply(TWO).add(ONE);
		} while (!possibleP.isProbablePrime(CERTAINTY));
		return new PrimePair(possibleP, possibleQ);
	}

}
