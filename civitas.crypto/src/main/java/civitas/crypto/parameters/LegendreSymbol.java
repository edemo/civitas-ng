package civitas.crypto.parameters;

import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.util.CivitasBigInteger;

@Controller
public class LegendreSymbol implements Constants {

	public int apply(CivitasBigInteger a, CivitasBigInteger p, CivitasBigInteger q) {
		CivitasBigInteger j = a.modPow(q, p);
		if (j.equals(ONE)) {
			return 1;
		} else if (j.equals(p.subtract(ONE))) {
			return -1;
		} else if (j.equals(ZERO)) {
			return 0;
		} else {
			throw new CryptoError("Impossible Legendre symbol");
		}
	}
}
