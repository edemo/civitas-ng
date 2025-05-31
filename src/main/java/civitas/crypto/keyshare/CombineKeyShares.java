package civitas.crypto.keyshare;

import civitas.crypto.Constants;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CombineKeyShares implements Constants {
	@Use
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	public ElGamalPublicKey apply(ElGamalKeyShare[] shares)
			throws CryptoException {
		if (shares == null)
			return null;
		CivitasBigInteger accum = ONE;
		ElGamalParameters params = null;
		for (ElGamalKeyShare s : shares) {
			// Check the proofs that this is a valid share
			try {
				if (params == null) {
					params = s.pubKey.params;
				}
				if (!verifyElGamalKeyShare.apply(s)) {
					throw new CryptoException("Invalid share");
				}
				// accumulate the keys..
				accum = accum.multiply(s.pubKey.y);
			} catch (NullPointerException e) {
				throw new CryptoException("Invalid share or proof", e);
			}
		}
		return new ElGamalPublicKey(accum, params);
	}

}
