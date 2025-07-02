package civitas.crypto.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.util.CivitasBigInteger;

@Controller
public class FindGenerator implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public CivitasBigInteger apply(PrimePair sp) {
		CivitasBigInteger g = null;
		boolean reject = false;
		CivitasBigInteger p = sp.p;
		CivitasBigInteger negONE = p.subtract(ONE);
		CivitasBigInteger twoK = p.subtract(ONE).divide(sp.q);
		do {
			g = cryptoBase.generateRandomElement(p);
			g = g.modPow(twoK, p);
			reject = g.equals(ONE) || g.equals(negONE);
		} while (reject);

		return g;
	}

}
