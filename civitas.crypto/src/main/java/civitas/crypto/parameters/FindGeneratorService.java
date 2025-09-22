package civitas.crypto.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.util.CivitasBigInteger;

@Service
public class FindGeneratorService implements Constants {
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
			reject = ONE.equals(g) || g.equals(negONE);
		} while (reject);

		return g;
	}
}
