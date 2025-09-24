package civitas.crypto.parameters.encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.LegendreSymbol;
import civitas.util.CivitasBigInteger;

@Controller
public class SafePrimeEncode {

	@Autowired
	LegendreSymbol legendreSymbol;

	public CivitasBigInteger apply(final CivitasBigInteger x, final ElGamalParameters elGamalParameters) {
		CivitasBigInteger encoding = x;
		if (legendreSymbol.apply(encoding, elGamalParameters.p, elGamalParameters.q) == -1) {
			encoding = elGamalParameters.p.subtract(encoding); // encoding = -m
		}
		return encoding;
	}
}
