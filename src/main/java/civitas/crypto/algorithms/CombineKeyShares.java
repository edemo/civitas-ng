package civitas.crypto.algorithms;

import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;

public class CombineKeyShares implements Constants {

	public ElGamalPublicKey apply(ElGamalKeyShare[] shares)
			throws CryptoException {
		if (shares == null)
			return null;
		CivitasBigInteger accum = ONE;
		ElGamalParameters params = null;
		for (int i = 0; i < shares.length; i++) {
			ElGamalKeyShare s = shares[i];

			// Check the proofs that this is a valid share
			try {
				if (params == null) {
					params = s.pubKey().getParams();
				}
				if (!s.verify()) {
					throw new CryptoException("Invalid share");
				}
			} catch (NullPointerException e) {
				throw new CryptoException("Invalid share or proof");
			}
			// accumulate the keys..
			if (s.pubKey() instanceof ElGamalPublicKeyC) {
				accum = accum.multiply(((ElGamalPublicKeyC) s.pubKey()).y);
			}
		}
		return new ElGamalPublicKeyC(accum, params);
	}

}
