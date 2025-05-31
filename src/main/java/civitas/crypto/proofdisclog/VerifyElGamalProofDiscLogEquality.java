package civitas.crypto.proofdisclog;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParameters;

public class VerifyElGamalProofDiscLogEquality {

	public boolean apply(ElGamalProofDiscLogEqualityC that,
			ElGamalParameters prms) {
		if (!(prms instanceof ElGamalParameters))
			return false;
		ElGamalParameters params = (ElGamalParameters) prms;

		try {
			// To verify, check that g_1^r = av^c (mod p) and g_2^r = bw^c (mod p)
			return that.g1.modPow(that.r, params.p)
					.equals(that.a.modMultiply(that.v.modPow(that.c, params.p), params.p))
					&& that.g2.modPow(that.r, params.p).equals(
							that.b.modMultiply(that.w.modPow(that.c, params.p), params.p));
		} catch (NullPointerException e) {
			return false;
		} catch (ArithmeticException e) {
			return false;
		}
	}

}
