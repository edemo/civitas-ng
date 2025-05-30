package civitas.crypto.decriptionshare;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElGamalMsgC;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;

public class CombineDecryptionShares implements Constants {

	public ElGamalMsg apply(ElGamalCiphertext c, ElGamalDecryptionShare[] shares,
			ElGamalParameters params) throws CryptoError {
		CivitasBigInteger prod = ONE;
		ElGamalCiphertextC cipher = (ElGamalCiphertextC) c;
		ElGamalParametersC ps = (ElGamalParametersC) params;
		for (ElGamalDecryptionShare share2 : shares) {
			ElGamalDecryptionShareC share = (ElGamalDecryptionShareC) share2;
			prod = prod.modMultiply(share.ai, ps.p);
		}
		CivitasBigInteger m = cipher.b.modDivide(prod, ps.p);
		return new ElGamalMsgC(m);
	}

}
