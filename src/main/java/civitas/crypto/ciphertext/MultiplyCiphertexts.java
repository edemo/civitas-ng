package civitas.crypto.ciphertext;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

public class MultiplyCiphertexts {

	public CiphertextList apply(ElGamalSignedCiphertext[][] ciphertexts,
			ElGamalParameters p) {
		if (ciphertexts == null)
			return null;
		try {
			ElGamalParameters params = p;
			// multiply all the shares together
			CivitasBigInteger[] aAccum = new CivitasBigInteger[ciphertexts[0].length];
			CivitasBigInteger[] bAccum = new CivitasBigInteger[ciphertexts[0].length];
			for (ElGamalSignedCiphertext[] ciphertext : ciphertexts) {
				for (int j = 0; j < ciphertext.length; j++) {
					ElGamalCiphertext s = (ElGamalCiphertext) ciphertext[j];
					if (aAccum[j] == null) {
						aAccum[j] = s.a;
						bAccum[j] = s.b;
					} else {
						aAccum[j] = aAccum[j].modMultiply(s.a, params.p);
						bAccum[j] = bAccum[j].modMultiply(s.b, params.p);
					}
				}
			}
			CiphertextList ret = new CiphertextList();
			for (int j = 0; j < aAccum.length; j++) {
				ret.add(new ElGamalCiphertext(aAccum[j], bAccum[j]));
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

}
