package civitas.crypto.decriptionshare;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

public class CombineDecryptionShares implements Constants {

	public ElGamalMsg apply(ElGamalCiphertextish c,
			ElGamalDecryptionShare[] shares, ElGamalParameters params)
			throws CryptoError {
		CivitasBigInteger prod = ONE;
		for (ElGamalDecryptionShare share2 : shares) {
			ElGamalDecryptionShare share = share2;
			prod = prod.modMultiply(share.ai, params.p);
		}
		CivitasBigInteger m = c.getB().modDivide(prod, params.p);
		return new ElGamalMsg(m);
	}

}
