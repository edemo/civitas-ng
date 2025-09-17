package civitas.crypto.proofdisclog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructElGamalDiscLogEqualityProof {
	@Autowired
	CryptoBase cryptoBase;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDiscLogEquality apply(ElGamalParameters params,
			CivitasBigInteger g1, CivitasBigInteger g2, CivitasBigInteger x) {

		CivitasBigInteger v = g1.modPow(x, params.p);
		CivitasBigInteger w = g2.modPow(x, params.p);

		CivitasBigInteger z = cryptoBase.generateRandomElement(params.q);
		CivitasBigInteger a = g1.modPow(z, params.p);
		CivitasBigInteger b = g2.modPow(z, params.p);

		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(v);
		l.add(w);
		l.add(a);
		l.add(b);
		byte[] hash = cryptoHash.apply(l);
		CivitasBigInteger c = convertHashToBigInt.apply(hash).mod(params.q);

		CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);

		return new ElGamalProofDiscLogEquality(g1, g2, v, w, a, b, c, r);
	}

}
