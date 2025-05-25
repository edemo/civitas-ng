package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class CombineDecryptionShares implements Constants {

	public ElGamalMsg apply(ElGamalCiphertext c, ElGamalDecryptionShare[] shares,
			ElGamalParameters params) throws CryptoError {
		CivitasBigInteger prod = ONE;
		try {
			ElGamalCiphertextC cipher = (ElGamalCiphertextC) c;
			ElGamalParametersC ps = (ElGamalParametersC) params;
			for (int i = 0; i < shares.length; i++) {
				ElGamalDecryptionShareC share = (ElGamalDecryptionShareC) shares[i];
				prod = prod.modMultiply(share.ai, ps.p);
			}
			CivitasBigInteger m = cipher.b.modDivide(prod, ps.p);
			return new ElGamalMsgC(m);
		} catch (RuntimeException e) {
			throw new CryptoError(e);
		}
	}

}
