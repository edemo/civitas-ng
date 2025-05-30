package civitas.crypto.keyshare;

import civitas.crypto.Constants;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
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
		for (int i = 0; i < shares.length; i++) {
			ElGamalKeyShare s = shares[i];

			// Check the proofs that this is a valid share
			try {
				if (params == null) {
					params = s.pubKey().getParams();
				}
				if (!verifyElGamalKeyShare.apply((ElGamalKeyShareC) s)) {
					throw new CryptoException("Invalid share");
				}
				// accumulate the keys..
				accum = accum.multiply(((ElGamalPublicKeyC) s.pubKey()).y);
			} catch (NullPointerException e) {
				throw new CryptoException("Invalid share or proof", e);
			}
		}
		return new ElGamalPublicKeyC(accum, params);
	}

}
