package civitas.crypto.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.util.CivitasBigInteger;

@Service
public class FindGenerator implements Constants {
	@Autowired
	public GenerateRandomElement generateRandomElement;

	public CivitasBigInteger apply(PrimePair sp) {
		CivitasBigInteger g = null;
		boolean reject = false;
		CivitasBigInteger p = sp.p;
		CivitasBigInteger negONE = p.subtract(ONE);
		CivitasBigInteger twoK = p.subtract(ONE).divide(sp.q);
		do {
			g = generateRandomElement.apply(p);
			g = g.modPow(twoK, p);
			reject = g.equals(ONE) || g.equals(negONE);
		} while (reject);

		return g;
	}

}
