package civitas.crypto.ciphertext;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class MultiplyCiphertexts {

	public CiphertextList apply(ElGamalCiphertextish[][] ciphertexts,
			ElGamalParameters parameters) {
		if (ciphertexts == null || ciphertexts[0] == null) {
			return new CiphertextList();
		}

		CivitasBigInteger[] aAccum = new CivitasBigInteger[ciphertexts[0].length];
		CivitasBigInteger[] bAccum = new CivitasBigInteger[ciphertexts[0].length];
		for (ElGamalCiphertextish[] ciphertext : ciphertexts) {
			for (int index = 0; index < ciphertext.length; index++) {
				ElGamalCiphertextish s = ciphertext[index];
				if (aAccum[index] == null) {
					aAccum[index] = s.getA();
					bAccum[index] = s.getB();
				} else {
					aAccum[index] = aAccum[index].modMultiply(s.getA(), parameters.p);
					bAccum[index] = bAccum[index].modMultiply(s.getB(), parameters.p);
				}
			}
		}
		CiphertextList result = new CiphertextList();
		for (int index = 0; index < aAccum.length; index++) {
			if (aAccum[index] == null || bAccum[index] == null) {
				return new CiphertextList();
			}
			result.add(new ElGamalCiphertext(aAccum[index], bAccum[index]));
		}
		return result;
	}

}
