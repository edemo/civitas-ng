package civitas.crypto.ciphertext;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class MultiplyCiphertexts {

	public CiphertextList apply(ElGamalCiphertextish[][] ciphertexts,
			ElGamalParameters p) {
		if (ciphertexts == null) {
			return null;
		}
		try {
			CivitasBigInteger[] aAccum = new CivitasBigInteger[ciphertexts[0].length];
			CivitasBigInteger[] bAccum = new CivitasBigInteger[ciphertexts[0].length];
			for (ElGamalCiphertextish[] ciphertext : ciphertexts) {
				for (int j = 0; j < ciphertext.length; j++) {
					ElGamalCiphertextish s = ciphertext[j];
					if (aAccum[j] == null) {
						aAccum[j] = s.getA();
						bAccum[j] = s.getB();
					} else {
						aAccum[j] = aAccum[j].modMultiply(s.getA(), p.p());
						bAccum[j] = bAccum[j].modMultiply(s.getB(), p.p());
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
		}
	}

}
