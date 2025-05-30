package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class MultiplyCiphertexts {

	public ElGamalCiphertext[] apply(ElGamalSignedCiphertext[][] ciphertexts,
			ElGamalParameters p) {
		if (ciphertexts == null)
			return null;
		try {
			ElGamalParametersC params = (ElGamalParametersC) p;
			// multiply all the shares together
			CivitasBigInteger[] aAccum = new CivitasBigInteger[ciphertexts[0].length];
			CivitasBigInteger[] bAccum = new CivitasBigInteger[ciphertexts[0].length];
			for (int i = 0; i < ciphertexts.length; i++) {
				for (int j = 0; j < ciphertexts[i].length; j++) {
					ElGamalCiphertextC s = (ElGamalCiphertextC) ciphertexts[i][j];
					if (aAccum[j] == null) {
						aAccum[j] = s.a;
						bAccum[j] = s.b;
					} else {
						aAccum[j] = aAccum[j].modMultiply(s.a, params.p);
						bAccum[j] = bAccum[j].modMultiply(s.b, params.p);
					}
				}
			}
			ElGamalCiphertext[] ret = new ElGamalCiphertext[aAccum.length];
			for (int j = 0; j < aAccum.length; j++) {
				ret[j] = new ElGamalCiphertextC(aAccum[j], bAccum[j]);
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

}
