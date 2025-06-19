package civitas.crypto.proofknowndisclog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;

@Service
public class ConstructProofKnowDiscLog {
	@Autowired
	GenerateRandomElement generateRandomElement;
	@Autowired
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
		CivitasBigInteger c = cryptoHash.apply(v, a).mod(params.q);
		CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);
		return new ElGamalProofKnowDiscLog(a, c, r, v);
	}

}
