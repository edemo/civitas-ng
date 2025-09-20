package civitas.crypto.keyshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class CombineKeyShares implements Constants {
	@Autowired
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	public ElGamalPublicKey apply(ElGamalKeyShare... shares) throws CryptoException {
		if (shares == null || shares.length == 0) {
			return null;
		}
		CivitasBigInteger product = ONE;
		ElGamalParameters params = null;
		for (ElGamalKeyShare share : shares) {
			if (share == null) {
				throw new CryptoException("Share is null");
			}
			// Check the proofs that this is a valid share
			if (params == null) {
				params = share.pubKey().params;
			}
			if (!verifyElGamalKeyShare.apply(share)) {
				throw new CryptoException("Invalid share");
			}
			// accumulate the keys...
			product = product.multiply(share.pubKey().y);
		}
		return new ElGamalPublicKey(product, params);
	}
}
