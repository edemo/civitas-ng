package civitas.crypto.proofknowndisclog;

import org.springframework.stereotype.Controller;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class VerifyElGamalProofKnowDiscLog {
	public boolean apply(ElGamalProofKnowDiscLog that, ElGamalParameters prms) {
		ElGamalParameters params = prms;
		try {
			CivitasBigInteger u = params.g.modPow(that.r, params.p);
			CivitasBigInteger w = that.a.modMultiply(that.v.modPow(that.c, params.p),
					params.p);

			return u.equals(w);
		} catch (NullPointerException e) {
			return false;
		} catch (ArithmeticException e) {
			return false;
		}
	}

}
