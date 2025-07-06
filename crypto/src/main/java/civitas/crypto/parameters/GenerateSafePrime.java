package civitas.crypto.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateSafePrime implements Constants {

	@Autowired
	CryptoBase cryptoBase;

	public PrimePair apply(int length) {
		CivitasBigInteger possibleP, possibleQ;
		do {
			possibleQ = cryptoBase.obtainProbablePrime(length);
			possibleP = possibleQ.multiply(TWO).add(ONE);
		} while (!possibleP.isProbablePrime(CERTAINTY));
		return new PrimePair(possibleP, possibleQ);
	}

}
