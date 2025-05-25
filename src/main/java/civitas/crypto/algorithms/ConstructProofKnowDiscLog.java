package civitas.crypto.algorithms;

import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPrivateKey;
import civitas.crypto.ElGamalProofKnowDiscLog;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.crypto.concrete.ElGamalProofKnowDiscLogC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructProofKnowDiscLog {
	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;

	public ElGamalProofKnowDiscLog apply(ElGamalParameters prms,
			ElGamalPrivateKey k) {
		if (k == null || !(k instanceof ElGamalPrivateKeyC)) {
			return null;
		}
		if (prms == null || !(prms instanceof ElGamalParametersC)) {
			return null;
		}
		ElGamalParametersC params = (ElGamalParametersC) prms;
		CivitasBigInteger x = ((ElGamalPrivateKeyC) k).x;
		CivitasBigInteger v = params.g.modPow(x, params.p);
		CivitasBigInteger z = generateRandomElement.apply(params.q);
		CivitasBigInteger a = params.g.modPow(z, params.p);
		CivitasBigInteger c = cryptoHash.apply(v, a).mod(params.q); // can take mod
																																// q
		// without
		// any ill effects.
		CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);
		return new ElGamalProofKnowDiscLogC(a, c, r, v);
	}

}
