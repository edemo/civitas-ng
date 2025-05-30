package civitas.crypto.algorithms;

import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProofKnowDiscLogC;
import civitas.util.CivitasBigInteger;

public class VerifyElGamalProofKnowDiscLog {
	public boolean apply(ElGamalProofKnowDiscLogC that, ElGamalParameters prms) {
		if (!(prms instanceof ElGamalParametersC))
			return false;
		ElGamalParametersC params = (ElGamalParametersC) prms;
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
