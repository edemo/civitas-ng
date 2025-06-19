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
		CivitasBigInteger v = prms.g.modPow(k.x, prms.p);
		CivitasBigInteger z = generateRandomElement.apply(prms.q);
		CivitasBigInteger a = prms.g.modPow(z, prms.p);
		CivitasBigInteger c = cryptoHash.apply(v, a).mod(prms.q);
		CivitasBigInteger r = z.modAdd(c.modMultiply(k.x, prms.q), prms.q);
		return new ElGamalProofKnowDiscLog(a, c, r, v);
	}

}
