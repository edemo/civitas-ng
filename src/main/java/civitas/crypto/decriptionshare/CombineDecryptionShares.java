package civitas.crypto.decriptionshare;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElGamalMsgC;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class CombineDecryptionShares implements Constants {

	public ElGamalMsg apply(ElGamalCiphertext c, ElGamalDecryptionShare[] shares,
			ElGamalParameters params) throws CryptoError {
		CivitasBigInteger prod = ONE;
		ElGamalCiphertext cipher = (ElGamalCiphertext) c;
		ElGamalParametersC ps = (ElGamalParametersC) params;
		for (ElGamalDecryptionShare share2 : shares) {
			ElGamalDecryptionShare share = (ElGamalDecryptionShare) share2;
			prod = prod.modMultiply(share.ai, ps.p);
		}
		CivitasBigInteger m = cipher.b.modDivide(prod, ps.p);
		return new ElGamalMsgC(m);
	}

}
