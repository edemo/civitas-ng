package civitas.crypto.proofknowndisclog;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructProofKnowDiscLog {
	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;

	public ElGamalProofKnowDiscLog apply(ElGamalParameters prms,
			ElGamalPrivateKey k) {
		if (k == null || !(k instanceof ElGamalPrivateKey) || prms == null
				|| !(prms instanceof ElGamalParameters)) {
			return null;
		}
		ElGamalParameters params = prms;
		CivitasBigInteger x = k.x;
		CivitasBigInteger v = params.g.modPow(x, params.p);
		CivitasBigInteger z = generateRandomElement.apply(params.q);
		CivitasBigInteger a = params.g.modPow(z, params.p);
		CivitasBigInteger c = cryptoHash.apply(v, a).mod(params.q); // can take mod
																																// q
		// without
		// any ill effects.
		CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);
		return new ElGamalProofKnowDiscLog(a, c, r, v);
	}

}
