package civitas.crypto.proofdisclog;

import org.springframework.stereotype.Controller;

import civitas.crypto.parameters.ElGamalParameters;

@Controller
public class VerifyElGamalProofDiscLogEquality {

	public boolean apply(ElGamalProofDiscLogEquality that,
			ElGamalParameters prms) {
		ElGamalParameters params = prms;
		try {
			return that.g1.modPow(that.r, params.p)
					.equals(that.a.modMultiply(that.v.modPow(that.c, params.p), params.p))
					&& that.g2.modPow(that.r, params.p).equals(
							that.b.modMultiply(that.w.modPow(that.c, params.p), params.p));
		} catch (NullPointerException | ArithmeticException e) {
			return false;
		}
	}

}
